package com.travelApi.repository;

import com.travelApi.entity.PassengerInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PassengerRepository extends JpaRepository<PassengerInfo, Integer> {

    List<PassengerInfo> findByIsDeletedIsFalse();

}
