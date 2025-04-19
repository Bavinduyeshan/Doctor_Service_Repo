package com.Doctor_service.Doctor_service.repositery;

import  com.Doctor_service.Doctor_service.model.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface DoctorRepositery extends JpaRepository<Doctor,Integer> {
    Doctor findByUserId(Integer userId);

}
