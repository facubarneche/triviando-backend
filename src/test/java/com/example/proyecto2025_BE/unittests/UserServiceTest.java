package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.UserRankingDTO;
import com.example.proyecto2025_BE.service.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserDao userDao;

    @InjectMocks
    private UserService userService;

    private List<User> users;

    private List<User> createUsers() {
        return Arrays.asList(
                User.builder().id(1L).fullName("Usuario Uno").score(new BigDecimal("100.00")).build(),
                User.builder().id(2L).fullName("Usuario Dos").score(new BigDecimal("200.00")).build(),
                User.builder().id(3L).fullName("Usuario Tres").score(new BigDecimal("300.00")).build(),
                User.builder().id(4L).fullName("Usuario Cuatro").score(new BigDecimal("400.00")).build(),
                User.builder().id(5L).fullName("Usuario Cinco").score(new BigDecimal("500.00")).build(),
                User.builder().id(6L).fullName("Usuario Seis").score(new BigDecimal("600.00")).build(),
                User.builder().id(7L).fullName("Usuario Siete").score(new BigDecimal("700.00")).build(),
                User.builder().id(8L).fullName("Usuario Ocho").score(new BigDecimal("800.00")).build(),
                User.builder().id(9L).fullName("Usuario Nueve").score(new BigDecimal("900.00")).build(),
                User.builder().id(10L).fullName("Usuario Diez").score(new BigDecimal("1000.00")).build(),
                User.builder().id(11L).fullName("Usuario Once").score(new BigDecimal("200.00")).build(),
                User.builder().id(12L).fullName("Usuario Doce").score(new BigDecimal("300.00")).build()
        );
    }

    @BeforeEach
    void setUp() {
        users = createUsers();
    }
    @AfterEach
    void tearDown() {
        Mockito.reset(userDao);  
    }

    private List<User> getSortedUsers() {
        return users.stream()
                .sorted(Comparator.comparing(User::getScore, Comparator.reverseOrder())
                        .thenComparing(User::getId))
                .collect(Collectors.toList());
    }

    private int findUserPosition(Long userId) {
        List<User> sortedUsers = getSortedUsers();
        for (int i = 0; i < sortedUsers.size(); i++) {
            if (sortedUsers.get(i).getId().equals(userId)) {
                return i;
            }
        }
        return -1;
    }

    @Test
    void getUsersOrderedByScoreDesc_ShouldReturnPageOfUsersOrderedByScore() {
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        List<User> sortedUsers = getSortedUsers();
        List<User> pagedUsers = sortedUsers.stream()
                .skip(0)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<User> expectedPage = new PageImpl<>(pagedUsers, pageable, sortedUsers.size());

        when(userDao.findAll(pageable)).thenReturn(expectedPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreDesc(pageable);

        assertNotNull(result);
        assertEquals(pageSize, result.getContent().size());
        assertEquals(new BigDecimal("1000.00"), result.getContent().getFirst().score());
        verify(userDao, times(1)).findAll(pageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_ShouldReturnCorrectPage() {
        Long userId = 4L;  // Usuario con score 400
        int pageSize = 5;
        Pageable pageable = PageRequest.of(0, pageSize); // La solicitud inicial puede ser página 0

        int userRankPosition = findUserPosition(userId);
        // Calcular el número de página esperado (base 0 para Pageable)
        int expectedPageNumberBase0 = userRankPosition / pageSize;
        // El número de página esperado para la respuesta (base 1)
        int expectedPageNumberBase1 = expectedPageNumberBase0 + 1;

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<User> sortedUsers = getSortedUsers();
        List<User> expectedContent = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0 * pageSize) // Usar el número de página base 0 para skip
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable expectedPageableBase0 = PageRequest.of(
                expectedPageNumberBase0, // Usar el número de página base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        Page<User> expectedPage = new PageImpl<>(expectedContent, expectedPageableBase0, sortedUsers.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(userRankPosition);
        when(userDao.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, pageable);

        assertNotNull(result);
        assertEquals(expectedPageNumberBase1, result.getNumber()); // Comparar con el número de página base 1
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserInFirstPage_ShouldReturnPageOne() {
        Long userId = 10L; // Usuario con el score más alto (1000)
        int pageSize = 5;
        Pageable initialPageable = PageRequest.of(0, pageSize); // Solicitud inicial

        int userRankPosition = findUserPosition(userId); // Debería ser 0
        int expectedPageNumberBase0 = userRankPosition / pageSize; // Debería ser 0
        int expectedPageNumberBase1 = expectedPageNumberBase0 + 1; // Debería ser 1 para la respuesta

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<User> sortedUsers = getSortedUsers();
        List<User> expectedContent = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0 * pageSize) // Usar base 0 para skip
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable expectedPageable = PageRequest.of(
                expectedPageNumberBase0, // Usar base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        Page<User> expectedPage = new PageImpl<>(expectedContent, expectedPageable, sortedUsers.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(userRankPosition);
        when(userDao.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, initialPageable);

        assertNotNull(result);
        assertEquals(expectedPageNumberBase1, result.getNumber()); // Aserción con base 1
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserInLastPage_ShouldReturnLastPage() {
        Long userId = 1L; // Usuario con score bajo (100)
        int pageSize = 5;
        Pageable initialPageable = PageRequest.of(0, pageSize); // Solicitud inicial

        int userRankPosition = findUserPosition(userId);
        int expectedPageNumberBase0 = userRankPosition / pageSize;
        int expectedPageNumberBase1 = expectedPageNumberBase0 + 1; // Para la respuesta

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<User> sortedUsers = getSortedUsers();
        List<User> expectedContent = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0 * pageSize) // Usar base 0
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable expectedPageable = PageRequest.of(
                expectedPageNumberBase0, // Usar base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        Page<User> expectedPage = new PageImpl<>(expectedContent, expectedPageable, sortedUsers.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(userRankPosition);
        when(userDao.findAll(any(Pageable.class))).thenReturn(expectedPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, initialPageable);

        assertNotNull(result);
        assertEquals(expectedPageNumberBase1, result.getNumber()); // Aserción con base 1
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAll(any(Pageable.class));
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserDoesNotExist_ShouldThrowNotFoundException() {
        Long userId = 999L;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(0, pageSize);

        when(userDao.findById(userId)).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getUsersOrderedByScoreFromUser(userId, pageable));
        verify(userDao, times(1)).findById(userId);
        verify(userDao, never()).findUserRankPosition(any());
        verify(userDao, never()).findAll(any(Pageable.class));
    }

    @Test
    void getUsersOrderedByScoreFromUser_UsersWithSameScore_ShouldUseDifferentPositions() {
        Long userId2 = 2L;  // Score 200
        Long userId11 = 11L; // Score 200 también
        int pageSize = 3;
        Pageable initialPageable = PageRequest.of(0, pageSize); // Solicitud inicial

        int userPosition2 = findUserPosition(userId2);
        int userPosition11 = findUserPosition(userId11);

        assertNotEquals(userPosition2, userPosition11);

        // Calcular el número de página esperado (base 0 para Pageable)
        int expectedPageNumberBase0_2 = userPosition2 / pageSize;
        int expectedPageNumberBase0_11 = userPosition11 / pageSize;

        // Calcular el número de página esperado para la respuesta (base 1)
        int expectedPageNumberBase1_2 = expectedPageNumberBase0_2 + 1;
        int expectedPageNumberBase1_11 = expectedPageNumberBase0_11 + 1;

        User user2 = users.stream().filter(u -> u.getId().equals(userId2)).findFirst().orElseThrow();
        User user11 = users.stream().filter(u -> u.getId().equals(userId11)).findFirst().orElseThrow();

        assertEquals(user2.getScore(), user11.getScore());

        List<User> sortedUsers = getSortedUsers();

        List<User> expectedContent2 = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0_2 * pageSize) // Usar base 0
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable expectedPageable2 = PageRequest.of(
                expectedPageNumberBase0_2, // Usar base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        Page<User> expectedPage2 = new PageImpl<>(expectedContent2, expectedPageable2, sortedUsers.size());

        List<User> expectedContent11 = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0_11 * pageSize) // Usar base 0
                .limit(pageSize)
                .collect(Collectors.toList());

        Pageable expectedPageable11 = PageRequest.of(
                expectedPageNumberBase0_11, // Usar base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        Page<User> expectedPage11 = new PageImpl<>(expectedContent11, expectedPageable11, sortedUsers.size());

        when(userDao.findById(userId2)).thenReturn(Optional.of(user2));
        when(userDao.findUserRankPosition(userId2)).thenReturn(userPosition2);
        when(userDao.findAll(eq(expectedPageable2))).thenReturn(expectedPage2);

        Page<UserRankingDTO> result2 = userService.getUsersOrderedByScoreFromUser(userId2, initialPageable);
        assertNotNull(result2);
        assertEquals(expectedPageNumberBase1_2, result2.getNumber()); // Aserción con base 1

        when(userDao.findById(userId11)).thenReturn(Optional.of(user11));
        when(userDao.findUserRankPosition(userId11)).thenReturn(userPosition11);
        when(userDao.findAll(eq(expectedPageable11))).thenReturn(expectedPage11);

        Page<UserRankingDTO> result11 = userService.getUsersOrderedByScoreFromUser(userId11, initialPageable);
        assertNotNull(result11);
        assertEquals(expectedPageNumberBase1_11, result11.getNumber()); // Aserción con base 1
    }

    @Test
    void getUsersOrderedByScoreFromUser_MultipleUsersWithSameScore_ShouldBeOrderedById() {
        Long userId3 = 3L;   // Score 300
        Long userId12 = 12L; // Score 300 también
        int pageSize = 3;
        Pageable initialPageable = PageRequest.of(0, pageSize); // La solicitud inicial puede ser página 0

        User user3 = users.stream().filter(u -> u.getId().equals(userId3)).findFirst().orElseThrow();
        User user12 = users.stream().filter(u -> u.getId().equals(userId12)).findFirst().orElseThrow();

        assertEquals(user3.getScore(), user12.getScore());

        List<User> sortedUsers = getSortedUsers();
        int userPosition3 = findUserPosition(userId3);
        int userPosition12 = findUserPosition(userId12);

        assertTrue(userPosition3 < userPosition12);

        when(userDao.findById(userId3)).thenReturn(Optional.of(user3));
        when(userDao.findUserRankPosition(userId3)).thenReturn(userPosition3);

        when(userDao.findById(userId12)).thenReturn(Optional.of(user12));
        when(userDao.findUserRankPosition(userId12)).thenReturn(userPosition12);

        // Calcular el número de página esperado (base 0 para Pageable)
        int expectedPageNumberBase0_3 = userPosition3 / pageSize;
        int expectedPageNumberBase0_12 = userPosition12 / pageSize;

        // Calcular el número de página esperado para la respuesta (base 1)
        int expectedPageNumberBase1_3 = expectedPageNumberBase0_3 + 1;
        int expectedPageNumberBase1_12 = expectedPageNumberBase0_12 + 1;

        Pageable expectedPageable3 = PageRequest.of(
                expectedPageNumberBase0_3, // Usar el número de página base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        Pageable expectedPageable12 = PageRequest.of(
                expectedPageNumberBase0_12, // Usar el número de página base 0 para el mock
                pageSize,
                Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id")));

        List<User> expectedContent3 = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0_3 * pageSize) // Usar el número de página base 0
                .limit(pageSize)
                .collect(Collectors.toList());

        List<User> expectedContent12 = sortedUsers.stream()
                .skip((long) expectedPageNumberBase0_12 * pageSize) // Usar el número de página base 0
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<User> expectedPage3 = new PageImpl<>(expectedContent3, expectedPageable3, sortedUsers.size());
        Page<User> expectedPage12 = new PageImpl<>(expectedContent12, expectedPageable12, sortedUsers.size());

        when(userDao.findAll(eq(expectedPageable3))).thenReturn(expectedPage3);
        when(userDao.findAll(eq(expectedPageable12))).thenReturn(expectedPage12);

        Page<UserRankingDTO> result3 = userService.getUsersOrderedByScoreFromUser(userId3, initialPageable);
        assertNotNull(result3);
        assertEquals(expectedPageNumberBase1_3, result3.getNumber()); // Comparar con el número de página base 1

        Page<UserRankingDTO> result12 = userService.getUsersOrderedByScoreFromUser(userId12, initialPageable);
        assertNotNull(result12);
        assertEquals(expectedPageNumberBase1_12, result12.getNumber()); // Comparar con el número de página base 1
    }
}