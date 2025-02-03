package br.com.willianbittencourt.gestao_vagas.modules.candidate;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Repository;

import java.util.UUID;

//Não necessida da anotação @Repository pois já extende de JpaRepository
public interface CandidateRepository extends JpaRepository<CandidateEntity, UUID> {

}
