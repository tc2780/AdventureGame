package model;

import static org.junit.jupiter.api.Assertions.*;
import model.Character;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class CharacterTest {
    private Character user;

    @BeforeEach
    public void setUp() {
        user = new Character("Bob");
    }

    @Test
    public void testSetName() {

    }

    @Test
    public void testSetProgress() {

    }
}