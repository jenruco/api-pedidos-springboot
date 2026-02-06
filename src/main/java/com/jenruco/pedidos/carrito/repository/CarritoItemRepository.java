package com.jenruco.pedidos.carrito.repository;

import com.jenruco.pedidos.carrito.entity.CarritoItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CarritoItemRepository extends JpaRepository<CarritoItem, Long> {
    List<CarritoItem> findByCarritoId(Long carritoId);
}
