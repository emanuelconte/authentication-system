package auth.java.models;

import auth.java.authentication_system.AuthenticationSystemApplication;
import auth.java.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(classes = AuthenticationSystemApplication.class)
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void shouldSaveUserToDatabase() {
        // Given
        User user = new User(null, "testUser", "password", "test@example.com", "ROLE_USER");

        // When
        User savedUser = userRepository.save(user);

        // Then
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testUser");
        assertThat(savedUser.getEmail()).isEqualTo("test@example.com");
    }

    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
}

