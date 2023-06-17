package com.softwareapplication.remarket.repository;

import com.softwareapplication.remarket.domain.TenderPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TenderPriceRepository extends JpaRepository<TenderPrice,Long> {

}
