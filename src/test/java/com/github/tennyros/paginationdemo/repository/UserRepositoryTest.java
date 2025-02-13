package com.github.tennyros.paginationdemo.repository;


import com.github.tennyros.paginationdemo.config.BaseIntegrationConfigTest;
import com.github.tennyros.paginationdemo.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.testcontainers.containers.output.OutputFrame;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j
class UserRepositoryTest extends BaseIntegrationConfigTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void testPagination() {
        userRepository.saveAll(List.of(
                new User(null, "Alice", "alice@yandex.com"),
                new User(null, "Bob", "bob@yandex.com"),
                new User(null, "Charlie", "charlie@yandex.com"),
                new User(null, "David", "david@yandex.com"),
                new User(null, "Eve", "eve@yandex.com"),
                new User(null, "Frank", "frank@yandex.com")
        ));

        Page<User> page1 = userRepository.findAll(PageRequest.of(0, 3));
        assertThat(page1.getContent()).hasSize(3);

        Page<User> page2 = userRepository.findAll(PageRequest.of(1, 3));
        assertThat(page2.getContent()).hasSize(3);

        assertThat(page1.getTotalPages()).isEqualTo(2);
        assertThat(page1.getTotalElements()).isEqualTo(6);

        log.info(container.getLogs(OutputFrame.OutputType.STDOUT));
    }
}
