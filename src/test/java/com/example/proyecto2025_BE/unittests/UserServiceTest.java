package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.model.dto.Racha;
import com.example.proyecto2025_BE.model.dto.UserRankingDTO;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.utils.ranking.UserRankingProjection;
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
import java.time.LocalDate;
import java.util.*;
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
    private List<MockUserRankingProjection> userProjections;

    private List<User> createUsers() {
        return Arrays.asList(
                User.builder().id(1L).fullName("Usuario Uno").username("user1").score(new BigDecimal("100.00")).build(),
                User.builder().id(2L).fullName("Usuario Dos").username("user2").score(new BigDecimal("200.00")).build(),
                User.builder().id(3L).fullName("Usuario Tres").username("user3").score(new BigDecimal("300.00")).build(),
                User.builder().id(4L).fullName("Usuario Cuatro").username("user4").score(new BigDecimal("400.00")).build(),
                User.builder().id(5L).fullName("Usuario Cinco").username("user5").score(new BigDecimal("500.00")).build(),
                User.builder().id(6L).fullName("Usuario Seis").username("user6").score(new BigDecimal("600.00")).build(),
                User.builder().id(7L).fullName("Usuario Siete").username("user7").score(new BigDecimal("700.00")).build(),
                User.builder().id(8L).fullName("Usuario Ocho").username("user8").score(new BigDecimal("800.00")).build(),
                User.builder().id(9L).fullName("Usuario Nueve").username("user9").score(new BigDecimal("900.00")).build(),
                User.builder().id(10L).fullName("Usuario Diez").username("user10").score(new BigDecimal("1000.00")).build(),
                User.builder().id(11L).fullName("Usuario Once").username("user11").score(new BigDecimal("200.00")).build(),
                User.builder().id(12L).fullName("Usuario Doce").username("user12").score(new BigDecimal("300.00")).build()
        );
    }

    private record MockUserRankingProjection(Long id, String username, BigDecimal score,
                                             Integer position) implements UserRankingProjection {
        @Override
        public Long getId() {
            return id;
        }

        @Override
        public String getUserName() {
            return username;
        }

        @Override
        public BigDecimal getScore() {
            return score;
        }

        @Override
        public Integer getPosition() {
            return position;
        }
    }

    @BeforeEach
    void setUp() {
        users = createUsers();

        List<User> sortedUsers = getSortedUsers();
        userProjections = new ArrayList<>();

        for (int i = 0; i < sortedUsers.size(); i++) {
            User user = sortedUsers.get(i);
            userProjections.add(new MockUserRankingProjection(
                    user.getId(),
                    user.getUsername(),
                    user.getScore(),
                    i + 1
            ));
        }
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
                return i + 1; // Las posiciones empiezan en 1, no en 0
            }
        }
        return -1;
    }

    @Test
    void getUsersOrderedByScoreDesc_ShouldReturnPageOfUsersOrderedByScore() {
        int pageNumber = 0;
        int pageSize = 5;
        Pageable pageable = PageRequest.of(pageNumber, pageSize);

        List<MockUserRankingProjection> pagedProjections = userProjections.stream()
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> projectionPage = new PageImpl<>(
                pagedProjections, pageable, userProjections.size());

        when(userDao.findAllUsersWithRank(pageable)).thenReturn((Page) projectionPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreDesc(pageable);

        assertNotNull(result);
        assertEquals(pageSize, result.getContent().size());
        assertEquals(new BigDecimal("1000.00"), result.getContent().get(0).score());
        assertEquals(1, result.getContent().get(0).position());  // El usuario con mayor score tiene posición 1
        verify(userDao, times(1)).findAllUsersWithRank(pageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_ShouldReturnCorrectPage() {
        Long userId = 4L;  // Usuario con score 400
        int pageSize = 5;
        Pageable requestPageable = PageRequest.of(0, pageSize);

        int userPosition = findUserPosition(userId);
        int pageNum = (userPosition - 1) / pageSize;
        Pageable targetPageable = PageRequest.of(pageNum, pageSize);

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<MockUserRankingProjection> pageContent = userProjections.stream()
                .skip((long) pageNum * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> projectionPage = new PageImpl<>(
                pageContent, targetPageable, userProjections.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(userPosition);
        when(userDao.findAllUsersWithRank(targetPageable)).thenReturn((Page) projectionPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, requestPageable);

        assertNotNull(result);
        assertEquals(pageSize, result.getContent().size());
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(targetPageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserInFirstPage_ShouldReturnPageOne() {
        Long userId = 10L; // Usuario con el score más alto (1000)
        int pageSize = 5;
        Pageable requestPageable = PageRequest.of(0, pageSize);

        int userPosition = findUserPosition(userId); // Debería ser 1
        int pageNum = (userPosition - 1) / pageSize; // Debería ser 0
        Pageable targetPageable = PageRequest.of(pageNum, pageSize);

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<MockUserRankingProjection> pageContent = userProjections.stream()
                .skip((long) pageNum * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> projectionPage = new PageImpl<>(
                pageContent, targetPageable, userProjections.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(userPosition);
        when(userDao.findAllUsersWithRank(targetPageable)).thenReturn((Page) projectionPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, requestPageable);

        assertNotNull(result);
        assertEquals(pageSize, result.getContent().size());
        assertEquals(1, result.getContent().get(0).position()); // Primera posición
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(targetPageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserInLastPage_ShouldReturnLastPage() {
        Long userId = 1L; // Usuario con score bajo (100)
        int pageSize = 5;
        Pageable requestPageable = PageRequest.of(0, pageSize);

        int userPosition = findUserPosition(userId);
        int pageNum = (userPosition - 1) / pageSize;
        Pageable targetPageable = PageRequest.of(pageNum, pageSize);

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<MockUserRankingProjection> pageContent = userProjections.stream()
                .skip((long) pageNum * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> projectionPage = new PageImpl<>(
                pageContent, targetPageable, userProjections.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(userPosition);
        when(userDao.findAllUsersWithRank(targetPageable)).thenReturn((Page) projectionPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, requestPageable);

        assertNotNull(result);
        assertEquals(Math.min(pageSize, userProjections.size() - pageNum * pageSize), result.getContent().size());
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(targetPageable);
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
        verify(userDao, never()).findAllUsersWithRank(any(Pageable.class));
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserPositionIsNull_ShouldReturnFirstPage() {
        Long userId = 5L;
        int pageSize = 5;
        Pageable requestPageable = PageRequest.of(0, pageSize);
        Pageable firstPageable = PageRequest.of(0, pageSize);

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        List<MockUserRankingProjection> firstPageContent = userProjections.stream()
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> firstPage = new PageImpl<>(
                firstPageContent, firstPageable, userProjections.size());

        when(userDao.findById(userId)).thenReturn(Optional.of(user));
        when(userDao.findUserRankPosition(userId)).thenReturn(null);
        when(userDao.findAllUsersWithRank(firstPageable)).thenReturn((Page) firstPage);

        Page<UserRankingDTO> result = userService.getUsersOrderedByScoreFromUser(userId, requestPageable);

        assertNotNull(result);
        assertEquals(pageSize, result.getContent().size());
        verify(userDao, times(1)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(firstPageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_UsersWithSameScore_ShouldUseDifferentPositions() {
        Long userId2 = 2L;  // Score 200
        Long userId11 = 11L; // Score 200 también
        int pageSize = 3;
        Pageable requestPageable = PageRequest.of(0, pageSize);

        int userPosition2 = findUserPosition(userId2);
        int userPosition11 = findUserPosition(userId11);

        assertNotEquals(userPosition2, userPosition11);

        int pageNum2 = (userPosition2 - 1) / pageSize;
        int pageNum11 = (userPosition11 - 1) / pageSize;

        Pageable targetPageable2 = PageRequest.of(pageNum2, pageSize);
        Pageable targetPageable11 = PageRequest.of(pageNum11, pageSize);

        User user2 = users.stream().filter(u -> u.getId().equals(userId2)).findFirst().orElseThrow();
        User user11 = users.stream().filter(u -> u.getId().equals(userId11)).findFirst().orElseThrow();

        assertEquals(user2.getScore(), user11.getScore());

        List<MockUserRankingProjection> pageContent2 = userProjections.stream()
                .skip((long) pageNum2 * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<MockUserRankingProjection> pageContent11 = userProjections.stream()
                .skip((long) pageNum11 * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> page2 = new PageImpl<>(
                pageContent2, targetPageable2, userProjections.size());

        Page<MockUserRankingProjection> page11 = new PageImpl<>(
                pageContent11, targetPageable11, userProjections.size());

        when(userDao.findById(userId2)).thenReturn(Optional.of(user2));
        when(userDao.findUserRankPosition(userId2)).thenReturn(userPosition2);
        when(userDao.findAllUsersWithRank(targetPageable2)).thenReturn((Page) page2);

        when(userDao.findById(userId11)).thenReturn(Optional.of(user11));
        when(userDao.findUserRankPosition(userId11)).thenReturn(userPosition11);
        when(userDao.findAllUsersWithRank(targetPageable11)).thenReturn((Page) page11);

        Page<UserRankingDTO> result2 = userService.getUsersOrderedByScoreFromUser(userId2, requestPageable);
        Page<UserRankingDTO> result11 = userService.getUsersOrderedByScoreFromUser(userId11, requestPageable);

        assertNotNull(result2);
        assertNotNull(result11);

        verify(userDao, times(1)).findById(userId2);
        verify(userDao, times(1)).findUserRankPosition(userId2);
        verify(userDao, times(2)).findAllUsersWithRank(targetPageable2);

        verify(userDao, times(1)).findById(userId11);
        verify(userDao, times(1)).findUserRankPosition(userId11);
        verify(userDao, times(2)).findAllUsersWithRank(targetPageable11);
    }

    @Test
    void getUsersOrderedByScoreFromUser_MultipleUsersWithSameScore_ShouldBeOrderedById() {
        Long userId3 = 3L;   // Score 300
        Long userId12 = 12L; // Score 300 también
        int pageSize = 3;
        Pageable requestPageable = PageRequest.of(0, pageSize);

        User user3 = users.stream().filter(u -> u.getId().equals(userId3)).findFirst().orElseThrow();
        User user12 = users.stream().filter(u -> u.getId().equals(userId12)).findFirst().orElseThrow();

        assertEquals(user3.getScore(), user12.getScore());

        int userPosition3 = findUserPosition(userId3);
        int userPosition12 = findUserPosition(userId12);

        // El usuario con ID más bajo debe tener mejor posición cuando tienen el mismo score
        assertTrue(userPosition3 < userPosition12);

        int pageNum3 = (userPosition3 - 1) / pageSize;
        int pageNum12 = (userPosition12 - 1) / pageSize;

        Pageable targetPageable3 = PageRequest.of(pageNum3, pageSize);
        Pageable targetPageable12 = PageRequest.of(pageNum12, pageSize);

        List<MockUserRankingProjection> pageContent3 = userProjections.stream()
                .skip((long) pageNum3 * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        List<MockUserRankingProjection> pageContent12 = userProjections.stream()
                .skip((long) pageNum12 * pageSize)
                .limit(pageSize)
                .collect(Collectors.toList());

        Page<MockUserRankingProjection> page3 = new PageImpl<>(
                pageContent3, targetPageable3, userProjections.size());

        Page<MockUserRankingProjection> page12 = new PageImpl<>(
                pageContent12, targetPageable12, userProjections.size());

        when(userDao.findById(userId3)).thenReturn(Optional.of(user3));
        when(userDao.findUserRankPosition(userId3)).thenReturn(userPosition3);
        when(userDao.findAllUsersWithRank(targetPageable3)).thenReturn((Page) page3);

        when(userDao.findById(userId12)).thenReturn(Optional.of(user12));
        when(userDao.findUserRankPosition(userId12)).thenReturn(userPosition12);
        when(userDao.findAllUsersWithRank(targetPageable12)).thenReturn((Page) page12);

        Page<UserRankingDTO> result3 = userService.getUsersOrderedByScoreFromUser(userId3, requestPageable);
        Page<UserRankingDTO> result12 = userService.getUsersOrderedByScoreFromUser(userId12, requestPageable);

        assertNotNull(result3);
        assertNotNull(result12);

        verify(userDao, times(1)).findById(userId3);
        verify(userDao, times(1)).findUserRankPosition(userId3);
        verify(userDao, times(2)).findAllUsersWithRank(targetPageable3);

        verify(userDao, times(1)).findById(userId12);
        verify(userDao, times(1)).findUserRankPosition(userId12);
        verify(userDao, times(2)).findAllUsersWithRank(targetPageable12);
    }

    @Test
    void getRachaUsuario_ShouldReturnUserRachaInfo() {
        Long userId = 1L;
        LocalDate today = LocalDate.now();
        int rachaActual = 5;

        User user = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        user.setRachaActual(rachaActual);
        user.setUltimaActividad(today);

        when(userDao.findById(userId)).thenReturn(Optional.of(user));

        Racha result = userService.getRachaUsuario(userId);

        assertNotNull(result);
        assertEquals(rachaActual, result.rachaActual());
        assertEquals(today, result.ultimaActividad());
        verify(userDao, times(1)).findById(userId);
    }
}