package com.yourcompany.soc.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.metamodel.EntityType;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/dbmanager")
public class DatabaseController {

    @PersistenceContext
    private EntityManager entityManager;

    @GetMapping
    public String dbManager(Model model) {
        List<String> entityNames = new ArrayList<>();
        for (EntityType<?> entityType : entityManager.getMetamodel().getEntities()) {
            entityNames.add(entityType.getName());
        }
        model.addAttribute("entities", entityNames);
        return "dbmanager";
    }

    @GetMapping("/{entityName}")
public String viewEntity(
        @PathVariable String entityName,
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        Model model) {
    
    // Get entity class
    Class<?> entityClass = entityManager.getMetamodel()
            .getEntities().stream()
            .filter(e -> e.getName().equals(entityName))
            .findFirst()
            .map(EntityType::getJavaType)
            .orElseThrow(() -> new EntityNotFoundException("Entity not found"));
    
    // Create query
    String jpql = "SELECT e FROM " + entityName + " e";
    List<?> entities = entityManager.createQuery(jpql, entityClass)
            .setFirstResult(page * size)
            .setMaxResults(size)
            .getResultList();
    
    // Count total items
    Long totalItems = (Long) entityManager.createQuery("SELECT COUNT(e) FROM " + entityName + " e")
            .getSingleResult();
    
    model.addAttribute("entityName", entityName);
    model.addAttribute("entities", entities);
    model.addAttribute("currentPage", page);
    model.addAttribute("totalPages", (int) Math.ceil((double) totalItems / size));
    
    return "entity-view";
}
}

