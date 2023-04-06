package com.jonasqf.myexpenses.owner;

import com.jonasqf.myexpenses.entities.Owner;
import com.jonasqf.myexpenses.repositories.OwnerRepository;
import com.jonasqf.myexpenses.services.OwnerService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    private OwnerService underTest;
    @Mock
    private OwnerRepository ownerRepository;
    private Owner owner;

    @BeforeEach
    void setUp() {
        underTest = new OwnerService(ownerRepository);
        owner = new Owner("Jonas", "Flach");
    }

    @Test
    void itShouldRegisterAnewOwner() {
        underTest.register(owner);
        verify(ownerRepository).save(owner);
    }

    @Test
    void itShouldFindAllOwners() {
        underTest.findAll();
        verify(ownerRepository).findAll();
    }

    @Test
    void itShouldDelete() {
        underTest.delete(owner);
        verify(ownerRepository).delete(owner);
    }

    @Test
    void itShouldFindById() {
        underTest.findById(owner.getId());
        verify(ownerRepository).findById(owner.getId());
    }

    @Test
    void itShouldUpdate() {
        underTest.update(owner);
        verify(ownerRepository).save(owner);
    }
}