package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.InfoUser;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.InfoUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InfoUserService {


    private final InfoUserRepository repository;

    // Método para registrar un InfoUser.
    public InfoUser registrarInfoUser(String lastname, String cc, String num_cel, String city_of_residence,
                                      String address, Date birthday_date) throws MyException {
        try {
            InfoUser infoUser = new InfoUser();
            infoUser.setLastname(lastname);
            infoUser.setCc(cc);
            infoUser.setNum_cel(num_cel);
            infoUser.setCity_of_residence(city_of_residence);
            infoUser.setAddress(address);
            infoUser.setBirthday_date(birthday_date);

            return repository.save(infoUser);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al guardar la información del usuario");
        }
    }

    // Método para actualizar InfoUser.
    public InfoUser actualizarInfoUser(Integer id, String lastname, String cc, String num_cel,
                                       String city_of_residence, String address, Date birthday_date) throws MyException {
        try {
            Optional<InfoUser> optionalInfoUser = repository.findById(id);

            if (optionalInfoUser.isPresent()) {
                InfoUser infoUser = optionalInfoUser.get();
                infoUser.setLastname(lastname);
                infoUser.setCc(cc);
                infoUser.setNum_cel(num_cel);
                infoUser.setCity_of_residence(city_of_residence);
                infoUser.setAddress(address);
                infoUser.setBirthday_date(birthday_date);

                return repository.save(infoUser);
            } else {
                throw new MyException("Información del usuario no encontrada con el ID: " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al actualizar la información del usuario");
        }
    }

    // Método para buscar InfoUser por id.
    public Optional<InfoUser> buscarInfoUserPorId(Integer id) {
        return repository.findById(id);
    }

    // Método para listar InfoUsers.
    public List<InfoUser> mostrarInfoUsers() {
        return repository.findAll();
    }

    // Método para eliminar InfoUser por id.
    public void deleteInfoUser(Integer id) throws MyException {
        var infoUser = repository.findById(id)
                .orElseThrow(() -> new MyException("Información del usuario no encontrada con el ID: " + id));

        repository.delete(infoUser);
    }

}
