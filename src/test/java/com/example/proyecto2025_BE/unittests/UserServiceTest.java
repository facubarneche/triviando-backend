package com.example.proyecto2025_BE.unittests;

import com.example.proyecto2025_BE.dao.UserDao;
import com.example.proyecto2025_BE.exceptions.NotFoundException;
import com.example.proyecto2025_BE.model.User;
import com.example.proyecto2025_BE.service.UserService;
import com.example.proyecto2025_BE.utils.UserRankingProjection;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;

import java.math.BigDecimal;
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
    private static Sort sort;
    private static String[] orders;
    private List<User> createUsers() {
        return Arrays.asList(
                User.builder().id(1L).username("Usuario Uno").score(new BigDecimal("100.00")).build(),
                User.builder().id(2L).username("Usuario Dos").score(new BigDecimal("200.00")).build(),
                User.builder().id(3L).username("Usuario Tres").score(new BigDecimal("300.00")).build(),
                User.builder().id(4L).username("Usuario Cuatro").score(new BigDecimal("400.00")).build(),
                User.builder().id(5L).username("Usuario Cinco").score(new BigDecimal("500.00")).build(),
                User.builder().id(6L).username("Usuario Seis").score(new BigDecimal("600.00")).build(),
                User.builder().id(7L).username("Usuario Siete").score(new BigDecimal("700.00")).build(),
                User.builder().id(8L).username("Usuario Ocho").score(new BigDecimal("800.00")).build(),
                User.builder().id(9L).username("Usuario Nueve").score(new BigDecimal("900.00")).build(),
                User.builder().id(10L).username("Usuario Diez").score(new BigDecimal("1000.00")).build(),
                User.builder().id(11L).username("Usuario Once").score(new BigDecimal("200.00")).build(),
                User.builder().id(12L).username("Usuario Doce").score(new BigDecimal("300.00")).build()
        );
    }

    @BeforeAll
    static void beforeAll() {
        sort = Sort.by(Sort.Direction.DESC, "score").and(Sort.by(Sort.Direction.ASC, "id"));
        orders = sort.stream()
                .map(order -> order.getProperty() + "," + order.getDirection().toString())
                .toArray(String[]::new);
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
        Pageable pageable = PageRequest.of(0, 5,sort);

        List<User> sortedUsers = getSortedUsers();
        List<UserRankingProjection> projections = new ArrayList<>();

        // Crear las proyecciones para los primeros 5 usuarios
        for (int i = 0; i < 5; i++) {
            User user = sortedUsers.get(i);
            int position = i + 1; // La posición empieza en 1
            projections.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), position));
        }

        Page<UserRankingProjection> projectionPage = new PageImpl<>(projections, pageable, sortedUsers.size());

        when(userDao.findAllUsersWithRank(pageable)).thenReturn(projectionPage);

        // Simular la recuperación de usuarios completos desde el DAO
        for (int i = 0; i < 5; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act
        Page<User> result = userService.getUsersOrderedByScoreDesc(pageable.getPageNumber(), pageable.getPageSize(), orders);

        // Assert
        assertNotNull(result);
        assertEquals(5, result.getContent().size());
        assertEquals(new BigDecimal("1000.00"), result.getContent().get(0).getScore());

        // Verificar que la posición se establece correctamente
        for (int i = 0; i < 5; i++) {
            assertEquals(i + 1, result.getContent().get(i).getPosition());
        }

        verify(userDao, times(1)).findAllUsersWithRank(pageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_ShouldReturnCorrectPage() {
        // Arrange
        Long userId = 4L;
        Pageable pageable = PageRequest.of(0, 5,sort);

        User targetUser = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        int userPosition = findUserPosition(userId);
        int pageNumber = userPosition / pageable.getPageSize();

        when(userDao.findById(userId)).thenReturn(Optional.of(targetUser));
        when(userDao.findUserRankPosition(userId)).thenReturn(userPosition + 1); // posición base-1

        // Mock para getUsersOrderedByScoreDesc que se llama dentro del método a probar
        Pageable expectedPageable = PageRequest.of(pageNumber, pageable.getPageSize(),sort);

        List<User> sortedUsers = getSortedUsers();
        List<UserRankingProjection> projections = new ArrayList<>();

        int startIdx = pageNumber * pageable.getPageSize();
        int endIdx = Math.min(startIdx + pageable.getPageSize(), sortedUsers.size());

        for (int i = startIdx; i < endIdx; i++) {
            User user = sortedUsers.get(i);
            projections.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage = new PageImpl<>(projections, expectedPageable, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable)).thenReturn(projectionPage);

        // Mock para convertProjectionToUser
        for (int i = startIdx; i < endIdx; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act
        Page<User> result = userService.getUsersOrderedByScoreFromUser(userId, expectedPageable.getPageNumber(),pageable.getPageSize(),orders);

        // Assert
        assertNotNull(result);
        assertEquals(pageNumber, result.getNumber());

        // Verify
        verify(userDao, times(2)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(expectedPageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserInFirstPage_ShouldReturnFirstPage() {
        // Arrange
        Long userId = 10L; // Usuario con mayor score (1000)
        Pageable pageable = PageRequest.of(0, 5,sort);

        User targetUser = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        // UserPosition debería ser 0 para este usuario (primera posición)
        int userPosition = findUserPosition(userId);
        int pageNumber = userPosition / pageable.getPageSize();

        // Verificar que está en la primera página
        assertEquals(0, pageNumber);

        when(userDao.findById(userId)).thenReturn(Optional.of(targetUser));
        when(userDao.findUserRankPosition(userId)).thenReturn(userPosition + 1); // posición base-1

        // Mock para getUsersOrderedByScoreDesc
        Pageable expectedPageable = PageRequest.of(pageNumber, pageable.getPageSize(),sort);

        List<User> sortedUsers = getSortedUsers();
        List<UserRankingProjection> projections = new ArrayList<>();

        for (int i = 0; i < pageable.getPageSize(); i++) {
            User user = sortedUsers.get(i);
            projections.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage = new PageImpl<>(projections, expectedPageable, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable)).thenReturn(projectionPage);

        // Mock para convertProjectionToUser
        for (int i = 0; i < pageable.getPageSize(); i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act
        Page<User> result = userService.getUsersOrderedByScoreFromUser(userId, pageable.getPageNumber(),pageable.getPageSize(),orders);

        // Assert
        assertNotNull(result);
        assertEquals(pageNumber, result.getNumber());

        // Verify
        verify(userDao, times(2)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(expectedPageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserInLastPage_ShouldReturnLastPage() {
        // Arrange
        Long userId = 1L; // Usuario con score bajo (100)
        Pageable pageable = PageRequest.of(0, 5);

        User targetUser = users.stream()
                .filter(u -> u.getId().equals(userId))
                .findFirst()
                .orElseThrow();

        int userPosition = findUserPosition(userId);
        int pageNumber = userPosition / pageable.getPageSize();

        when(userDao.findById(userId)).thenReturn(Optional.of(targetUser));
        when(userDao.findUserRankPosition(userId)).thenReturn(userPosition + 1); // posición base-1

        // Mock para getUsersOrderedByScoreDesc
        Pageable expectedPageable = PageRequest.of(pageNumber, pageable.getPageSize(),sort);

        List<User> sortedUsers = getSortedUsers();
        List<UserRankingProjection> projections = new ArrayList<>();

        int startIdx = pageNumber * pageable.getPageSize();
        int endIdx = Math.min(startIdx + pageable.getPageSize(), sortedUsers.size());

        for (int i = startIdx; i < endIdx; i++) {
            User user = sortedUsers.get(i);
            projections.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage = new PageImpl<>(projections, expectedPageable, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable)).thenReturn(projectionPage);

        // Mock para convertProjectionToUser
        for (int i = startIdx; i < endIdx; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act
        Page<User> result = userService.getUsersOrderedByScoreFromUser(userId, projectionPage.getNumber(),pageable.getPageSize(),orders);

        // Assert
        assertNotNull(result);
        assertEquals(pageNumber, result.getNumber());

        // Verify
        verify(userDao, times(2)).findById(userId);
        verify(userDao, times(1)).findUserRankPosition(userId);
        verify(userDao, times(1)).findAllUsersWithRank(expectedPageable);
    }

    @Test
    void getUsersOrderedByScoreFromUser_UserDoesNotExist_ShouldThrowNotFoundException() {
        // Arrange
        Long userId = 999L;
        Pageable pageable = PageRequest.of(0, 5);

        when(userDao.findById(userId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(NotFoundException.class, () -> userService.getUsersOrderedByScoreFromUser(userId, pageable.getPageNumber(),pageable.getPageSize(),orders));

        // Verify
        verify(userDao, times(1)).findById(userId);
        verify(userDao, never()).findUserRankPosition(any());
        verify(userDao, never()).findAllUsersWithRank(any(Pageable.class));
    }

    @Test
    void getUsersOrderedByScoreFromUser_UsersWithSameScore_ShouldUseDifferentPositions() {
        // Arrange
        Long userId2 = 2L;  // Score: 200
        Long userId11 = 11L; // Score: 200
        Pageable pageable = PageRequest.of(0, 3,sort);

        User user2 = users.stream().filter(u -> u.getId().equals(userId2)).findFirst().orElseThrow();
        User user11 = users.stream().filter(u -> u.getId().equals(userId11)).findFirst().orElseThrow();

        // Verificar que tienen el mismo score
        assertEquals(user2.getScore(), user11.getScore());

        int position2 = findUserPosition(userId2);
        int position11 = findUserPosition(userId11);

        // Verificar que tienen posiciones diferentes
        assertNotEquals(position2, position11);

        int pageNumber2 = position2 / pageable.getPageSize();
        int pageNumber11 = position11 / pageable.getPageSize();

        // Test para el primer usuario
        when(userDao.findById(userId2)).thenReturn(Optional.of(user2));
        when(userDao.findUserRankPosition(userId2)).thenReturn(position2 + 1);

        Pageable expectedPageable2 = PageRequest.of(pageNumber2, pageable.getPageSize(),sort);

        List<User> sortedUsers = getSortedUsers();
        List<UserRankingProjection> projections2 = new ArrayList<>();

        int startIdx2 = pageNumber2 * pageable.getPageSize();
        int endIdx2 = Math.min(startIdx2 + pageable.getPageSize(), sortedUsers.size());

        for (int i = startIdx2; i < endIdx2; i++) {
            User user = sortedUsers.get(i);
            projections2.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage2 = new PageImpl<>(projections2, expectedPageable2, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable2)).thenReturn(projectionPage2);

        // Mock para convertProjectionToUser
        for (int i = startIdx2; i < endIdx2; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act para usuario 2
        Page<User> result2 = userService.getUsersOrderedByScoreFromUser(userId2, projectionPage2.getNumber(),pageable.getPageSize(),orders);

        // Assert para usuario 2
        assertNotNull(result2);
        assertEquals(pageNumber2, result2.getNumber());

        // Resetear mocks para el segundo usuario
        Mockito.reset(userDao);

        // Test para el segundo usuario
        when(userDao.findById(userId11)).thenReturn(Optional.of(user11));
        when(userDao.findUserRankPosition(userId11)).thenReturn(position11 + 1);

        Pageable expectedPageable11 = PageRequest.of(pageNumber11, pageable.getPageSize(),sort);

        List<UserRankingProjection> projections11 = new ArrayList<>();

        int startIdx11 = pageNumber11 * pageable.getPageSize();
        int endIdx11 = Math.min(startIdx11 + pageable.getPageSize(), sortedUsers.size());

        for (int i = startIdx11; i < endIdx11; i++) {
            User user = sortedUsers.get(i);
            projections11.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage11 = new PageImpl<>(projections11, expectedPageable11, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable11)).thenReturn(projectionPage11);

        // Mock para convertProjectionToUser
        for (int i = startIdx11; i < endIdx11; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act para usuario 11
        Page<User> result11 = userService.getUsersOrderedByScoreFromUser(userId11, projectionPage11.getNumber(),pageable.getPageSize(),orders);

        // Assert para usuario 11
        assertNotNull(result11);
        assertEquals(pageNumber11, result11.getNumber());
    }

    @Test
    void getUsersOrderedByScoreFromUser_MultipleUsersWithSameScore_ShouldBeOrderedById() {
        // Arrange
        Long userId3 = 3L;  // Score: 300
        Long userId12 = 12L; // Score: 300

        Pageable pageable = PageRequest.of(0, 3,sort);

        User user3 = users.stream().filter(u -> u.getId().equals(userId3)).findFirst().orElseThrow();
        User user12 = users.stream().filter(u -> u.getId().equals(userId12)).findFirst().orElseThrow();

        // Verificar que tienen el mismo score
        assertEquals(user3.getScore(), user12.getScore());

        int position3 = findUserPosition(userId3);
        int position12 = findUserPosition(userId12);

        // Verificar que user3 tiene una posición anterior a user12 (ordenados por ID)
        assertTrue(position3 < position12);

        // Test para el primer usuario
        when(userDao.findById(userId3)).thenReturn(Optional.of(user3));
        when(userDao.findUserRankPosition(userId3)).thenReturn(position3 + 1);

        int pageNumber3 = position3 / pageable.getPageSize();
        Pageable expectedPageable3 = PageRequest.of(pageNumber3, pageable.getPageSize(),sort);

        List<User> sortedUsers = getSortedUsers();
        List<UserRankingProjection> projections3 = new ArrayList<>();

        int startIdx3 = pageNumber3 * pageable.getPageSize();
        int endIdx3 = Math.min(startIdx3 + pageable.getPageSize(), sortedUsers.size());

        for (int i = startIdx3; i < endIdx3; i++) {
            User user = sortedUsers.get(i);
            projections3.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage3 = new PageImpl<>(projections3, expectedPageable3, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable3)).thenReturn(projectionPage3);

        // Mock para convertProjectionToUser
        for (int i = startIdx3; i < endIdx3; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act para usuario 3
        Page<User> result3 = userService.getUsersOrderedByScoreFromUser(userId3, projectionPage3.getNumber(),pageable.getPageSize(),orders);

        // Assert para usuario 3
        assertNotNull(result3);
        assertEquals(pageNumber3, result3.getNumber());

        // Resetear mocks para el segundo usuario
        Mockito.reset(userDao);

        // Test para el segundo usuario
        when(userDao.findById(userId12)).thenReturn(Optional.of(user12));
        when(userDao.findUserRankPosition(userId12)).thenReturn(position12 + 1);

        int pageNumber12 = position12 / pageable.getPageSize();
        Pageable expectedPageable12 = PageRequest.of(pageNumber12, pageable.getPageSize(),sort);

        List<UserRankingProjection> projections12 = new ArrayList<>();

        int startIdx12 = pageNumber12 * pageable.getPageSize();
        int endIdx12 = Math.min(startIdx12 + pageable.getPageSize(), sortedUsers.size());

        for (int i = startIdx12; i < endIdx12; i++) {
            User user = sortedUsers.get(i);
            projections12.add(new UserRankingProjectionImpl(user.getId(), user.getUsername(), user.getScore(), i + 1));
        }

        Page<UserRankingProjection> projectionPage12 = new PageImpl<>(projections12, expectedPageable12, sortedUsers.size());

        when(userDao.findAllUsersWithRank(expectedPageable12)).thenReturn(projectionPage12);

        // Mock para convertProjectionToUser
        for (int i = startIdx12; i < endIdx12; i++) {
            User user = sortedUsers.get(i);
            when(userDao.findById(user.getId())).thenReturn(Optional.of(user));
        }

        // Act para usuario 12
        Page<User> result12 = userService.getUsersOrderedByScoreFromUser(userId12, projectionPage12.getNumber(),pageable.getPageSize(),orders);

        // Assert para usuario 12
        assertNotNull(result12);
        assertEquals(pageNumber12, result12.getNumber());
    }

    private Pageable createPageable(int page, int size, String[] sortParams) {
        if (sortParams == null || sortParams.length == 0) {
            return PageRequest.of(page, size);
        }

        List<Sort.Order> orders = Arrays.stream(sortParams)
                .map(param -> {
                    String[] parts = param.split(",");
                    String property = parts[0];
                    Sort.Direction direction = (parts.length > 1 && parts[1].equalsIgnoreCase("desc"))
                            ? Sort.Direction.DESC
                            : Sort.Direction.ASC;
                    return new Sort.Order(direction, property);
                })
                .collect(Collectors.toList());

        return PageRequest.of(page, size ,sort);
    }

    @AllArgsConstructor
    private static class UserRankingProjectionImpl implements UserRankingProjection {
        private Long id;
        private String userName;
        private BigDecimal score;
        private Integer position;

        @Override
        public Long getId() {
            return id;
        }

        @Override
        public String getUserName() {
            return userName;
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
}