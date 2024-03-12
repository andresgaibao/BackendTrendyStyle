package com.proyecto.trendy.services;

import com.proyecto.trendy.repository.CartItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
public class CartItemService {

    private final CartItemRepository repository;

    public CartItemService(CartItemRepository repository) {
        this.repository = repository;
    }


}
