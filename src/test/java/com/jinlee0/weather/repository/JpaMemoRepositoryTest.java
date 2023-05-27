package com.jinlee0.weather.repository;

import com.jinlee0.weather.domain.Memo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
@Transactional
class JpaMemoRepositoryTest {

    @Autowired
    private JpaMemoRepository jpaMemoRepository;

    @Test
    void insert() {
        String text = UUID.randomUUID().toString();
        Memo newMemo = jpaMemoRepository.save(new Memo(1, text));

        jpaMemoRepository.save(newMemo);

        List<Memo> memos = jpaMemoRepository.findAll();
        assertTrue(memos.size() > 0);
        assertEquals(newMemo.getText(), text);
    }
}