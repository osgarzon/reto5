package com.reto5.modelo;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepositorio extends CrudRepository<Producto, Integer>{
    @Query("SELECT nombre FROM productos ORDER BY precio DESC limit 1")
    public String getProductoPrecioMayor();
    
    @Query("SELECT nombre FROM productos ORDER BY precio ASC limit 1")
    public String getProductoPrecioMenor();
    
    @Query("SELECT AVG (precio) FROM productos")
    public String getPromedioPrecio();
    
    @Query("SELECT SUM(inventario*precio) FROM productos")
    public String getTotalInventario();
}
