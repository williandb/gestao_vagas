package br.com.willianbittencourt.gestao_vagas.modules.candidate.useCases;

import br.com.willianbittencourt.gestao_vagas.exceptions.UserFoundException;
import br.com.willianbittencourt.gestao_vagas.modules.company.entities.CompanyEntity;
import br.com.willianbittencourt.gestao_vagas.modules.company.repositories.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreateCompanyUseCase {

    @Autowired
    private CompanyRepository companyRepository;

    public CompanyEntity execute(CompanyEntity companyEntity){
        this.companyRepository
                .findByUsernameOrEmail(companyEntity.getUsername(), companyEntity.getEmail())
                .ifPresent((User) -> {
                    throw new UserFoundException();
                });
        return this.companyRepository.save(companyEntity);
    }
}
