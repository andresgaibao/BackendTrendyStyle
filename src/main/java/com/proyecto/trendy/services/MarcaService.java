package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.exceptions.MyException;
import com.proyecto.trendy.repository.MarcaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MarcaService {

    private final MarcaRepository repository;

    //Método para registrar una marca.
    public Marca registrarMarca(MultipartFile img, String name) throws MyException {
        if (img != null) {
            try {
                Marca marca = new Marca();
                marca.setName(name);
                marca.setImg(img.getBytes());

                repository.save(marca);
            } catch (Exception e) {
                System.err.println(e.getMessage());
                throw new MyException("Error al guardar la marca");
            }
        }
        return null;
    }

    //Métodopara actualizar marcas.
    public Marca actualizarMarca(Integer id, MultipartFile img, String name) throws MyException {
        if (img != null) {
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
        return null;
    }

    //Método para buscar marca por id.
    public Optional<Marca> buscarMarcaPorId(Integer id){

        return repository.findById(id);
    }

    //Método para listar marcas.
    public List<Marca> mostrarMarcas(){

        return repository.findAll();
    }

    public void deleteMarca(Integer id) {
        var marca = repository.findById(id)
                .orElseThrow();

        repository.delete(marca);
    }
}
