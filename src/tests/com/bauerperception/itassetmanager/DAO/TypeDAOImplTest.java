package com.bauerperception.itassetmanager.DAO;

import com.bauerperception.itassetmanager.model.AssetEntity;
import com.bauerperception.itassetmanager.util.FXUtil;
import com.bauerperception.itassetmanager.util.TimeUtil;
import javafx.collections.ObservableList;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import java.sql.Statement;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class TypeDAOImplTest {
    @Test
    public void getAllTypes() throws Exception {
        //Predefined types that are inputted into the database
        ObservableList<String> allTypes = TypeDAOImpl.getAllTypes();
        assertEquals(17, allTypes.size());
        //A few chosen at random
        assertEquals("Mobile Phone", allTypes.stream().filter(a -> Objects.equals(a, "Mobile Phone")).collect(Collectors.toList()).get(0));
        assertEquals("Keyboard", allTypes.stream().filter(a -> Objects.equals(a, "Keyboard")).collect(Collectors.toList()).get(0));
        assertEquals("Docking Station", allTypes.stream().filter(a -> Objects.equals(a, "Docking Station")).collect(Collectors.toList()).get(0));
        assertEquals("Computer Tower", allTypes.stream().filter(a -> Objects.equals(a, "Computer Tower")).collect(Collectors.toList()).get(0));
    }
}