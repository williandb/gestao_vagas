package br.com.willianbittencourt.gestao_vagas.exceptions;

public class JobNotFoundException extends RuntimeException {
  public JobNotFoundException(String message) {
    super(message);
  }
}
