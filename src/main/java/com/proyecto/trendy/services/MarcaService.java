package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository repository;

    //Método para registrar una marca.
    public Marca registrarMarca(MultipartFile img, String name) throws MyException {
        if (img == null || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("La imagen y el nombre de la marca no pueden ser nulos o vacíos");
        }

        try {
            Marca marca = new Marca();
            marca.setName(name);
            marca.setImg(img.getBytes());

            return repository.save(marca);
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al guardar la marca");
        }
    }


    //Métodopara actualizar marcas.
    public Marca actualizarMarca(Integer id, MultipartFile img, String name) throws MyException {
        if (img == null || name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("La imagen y el nombre de la marca no pueden ser nulos o vacíos");
        }

        try {
            Optional<Marca> optionalMarca = repository.findById(id);

            if (optionalMarca.isPresent()) {
                Marca marca = optionalMarca.get();
                marca.setName(name);
                marca.setImg(img.getBytes());

                return repository.save(marca);
            } else {
                throw new MyException("Marca no encontrada con el ID: " + id);
            }
        } catch (Exception e) {
            System.err.println(e.getMessage());
            throw new MyException("Error al actualizar la marca");
        }
    }


    //Método para listar marcas.
    public List<Marca> mostrarMarcas() throws MyException {
        List<Marca> marcas = repository.findAll();
        if (marcas.isEmpty()) {
            throw new MyException("No hay marcas registradas en el sistema");
        }
        return marcas;
    }


    //Método para eliminar marca.
    public void deleteMarca(Integer id) throws MyException {
        var marca = repository.findById(id)
                .orElseThrow(() -> new MyException("Marca no encontrada con el ID: " + id));

        repository.delete(marca);
    }

    // Buscar marca por su id.
    public Marca getMarcaById(Integer id) {
        Optional<Marca> optionalMarca = repository.findById(id);
        return optionalMarca.orElseThrow(() -> new EntityNotFoundException("Marca no encontrada con el ID: " + id));
    }
}
