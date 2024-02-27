package com.proyecto.trendy.services;

import com.proyecto.trendy.entity.Marca;
import com.proyecto.trendy.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class MarcasService {

    private final MarcaRepository marcaRepository;

    @Autowired
    public MarcasService(MarcaRepository marcaRepository) {
        this.marcaRepository = marcaRepository;
    }

    //Método para crear una marca.
    public Marca createMarca(Marca marca /*MultipartFile logo*/) throws IOException {
        Marca nuevaMarca = new Marca();
        nuevaMarca.setName(marca.getName());

        if (marca.getName() == null || marca.getName().isEmpty()) {
            throw new IllegalArgumentException("El nombre de la marca no puede estar vacío");
        }

        /*if (logo != null) {
            try {
                nuevaMarca.setLogo(logo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("No se pudo obtener bytes del logo actualizado", e);
            }
        }*/
        return marcaRepository.save(nuevaMarca);
    }

    //Método para actualizar una marca
    public Marca updateMarca(Long id, Marca updatedMarca /*MultipartFile updatedLogo*/) {
        Marca existingMarca = marcaRepository.findById(id)
                .orElseThrow();

        existingMarca.setName(updatedMarca.getName());

        /*if (updatedLogo != null) {
            try {
                existingMarca.setLogo(updatedLogo.getBytes());
            } catch (IOException e) {
                throw new RuntimeException("No se pudo obtener bytes del logo actualizado", e);
            }
        }*/
        return marcaRepository.save(existingMarca);
    }

    //Método para eliminar marca
    public void deleteMarca(Long id) {
        marcaRepository.deleteById(id);
    }

    public Marca getMarcaById(Long id) {
        return marcaRepository.findById(id)
                .orElseThrow();
    }

}
