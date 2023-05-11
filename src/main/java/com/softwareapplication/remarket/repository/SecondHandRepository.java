package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.SecondHand;
import com.softwareapplication.remarket.dto.SecondHandDto;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SecondHandRepository extends JpaRepository<SecondHand,Long> {

    List<SecondHand> findByTitleContaining(String keyword); //해당 키워드 post 찾기

}
