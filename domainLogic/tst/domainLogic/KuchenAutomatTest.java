package domainLogic;

import domainLogic.cake.CakeProduct;
import domainLogic.cake.KremkuchenImpl;
import domainLogic.cake.parts.Krem;
import domainLogic.cake.parts.Obst;
import kuchen.Allergen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class KuchenAutomatTest {

    private HerstellerVerwaltung herstellerVerwaltung;
    private Hersteller hersteller;
    private KuchenAutomat kuchenAutomat;
    private HashSet<Allergen> allergens;

    @BeforeEach
    void setUp() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        hersteller = new HerstellerImpl("peter");
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);
        allergens = new HashSet<>();
        allergens.add(Allergen.Erdnuss);
    }

    @Test
    void createCallsHerstellerVerwaltung() {
        HerstellerVerwaltung mockHerstellerVerwaltung = mock(HerstellerVerwaltung.class);
        when(mockHerstellerVerwaltung.getHersteller("peter")).thenReturn(hersteller);

        KuchenAutomat kuchenAutomat = new KuchenAutomat(100, mockHerstellerVerwaltung);
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));

        verify(mockHerstellerVerwaltung, times(1)).containsHersteller(hersteller);
    }

    @Test
    void deleteCallsRemoveOnKuchenAutomat() {
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));

        KuchenAutomat mockedKuchenAutomat = mock(KuchenAutomat.class);
        mockedKuchenAutomat.delete(1);

        verify(mockedKuchenAutomat, times(1)).delete(1);
    }

    @Test
    void createInserts() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        assertEquals(1, kuchenAutomat.read().size());
    }

    @Test
    void readReturnsList() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

        HashSet<Allergen> allergensTwo = new HashSet<>();
        allergensTwo.add(Allergen.Gluten);

        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(2398.123), 913, allergensTwo, new Obst("Apfel"));

        ArrayList<CakeProduct> listToCompare = new ArrayList<>();
        listToCompare.add(kuchenAutomat.read(1));
        listToCompare.add(kuchenAutomat.read(2));

        assertEquals(listToCompare, kuchenAutomat.read());
    }

    @Test
    void deleteRemoves() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        kuchenAutomat.delete(1);
        assertEquals(0, kuchenAutomat.read().size());
    }

    @Test
    void updateChangesDate() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        Date now = Date.from(Instant.now());
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        kuchenAutomat.update(1);
        assertTrue(now.before(kuchenAutomat.read(1).getInspektionsdatum()));
    }

    @Test
    void testCreateAlleKuchenSorten() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Obst("Apfel"));
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"), new Obst("Erdbeere"));
        assertEquals(3, kuchenAutomat.read().size());
    }

    @Test
    void testReadCakesByClass() {
        herstellerVerwaltung = new HerstellerVerwaltung();
        herstellerVerwaltung.addHersteller(hersteller);
        kuchenAutomat = new KuchenAutomat(100, herstellerVerwaltung);

        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Obst("Birne"));
        assertEquals(3, kuchenAutomat.read(KremkuchenImpl.class).size());
    }

    @Test
    void testCapacity() {
        kuchenAutomat = new KuchenAutomat(10, herstellerVerwaltung);
        for (int i = 1; i <= 20; i++ ) {
            kuchenAutomat.create(hersteller, BigDecimal.valueOf(12398.123), 9123, allergens, new Krem("Karamel"));
        }
        assertEquals(10, kuchenAutomat.read().size());
    }
}


