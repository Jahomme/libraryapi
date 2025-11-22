package teste.estudos.libraryapi.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import teste.estudos.libraryapi.model.Client;
import teste.estudos.libraryapi.repository.ClientRepository;

@Service
@RequiredArgsConstructor
public class ClientService {

    private final ClientRepository repository;
    private final PasswordEncoder encoder;

    public Client salvar(Client client){
        var senhaCriptografada = encoder.encode(client.getClientSecret());
        client.setClientSecret(senhaCriptografada);
        return repository.save(client);
    }

    public Client obterPorClientId(String clientId){
        return repository.findByClientId(clientId);
    }
}
