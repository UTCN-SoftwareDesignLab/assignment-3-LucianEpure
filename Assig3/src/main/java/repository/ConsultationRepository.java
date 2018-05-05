package repository;

import entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import entity.Consultation;

import java.util.List;

public interface ConsultationRepository extends JpaRepository<Consultation,Integer>{

    public List<Consultation> findByDoctor(User doctor);
}
