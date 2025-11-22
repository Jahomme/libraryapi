package teste.estudos.libraryapi.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import teste.estudos.libraryapi.model.Client;

import java.util.UUID;

public interface ClientRepository extends JpaRepository<Client, UUID> {
    Client findByClientId(String clientId);
}
