package com.example.Second.repositories;

import com.example.Second.entities.PatientReservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PatientReservationRepository extends JpaRepository<PatientReservation, Long> {

    @Query(value = "SELECT * from patients_reservations WHERE is_active = 1", nativeQuery = true)
    List<PatientReservation> findAll();

    PatientReservation findByFullName(String patientName);
}
