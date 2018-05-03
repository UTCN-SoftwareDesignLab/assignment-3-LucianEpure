package repository;

import org.springframework.data.jpa.repository.JpaRepository;

import entity.Consultation;

public interface ConsultationRepository extends JpaRepository<Consultation,Integer>{

}
