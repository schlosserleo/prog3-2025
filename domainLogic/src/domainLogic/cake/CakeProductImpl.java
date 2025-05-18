package domainLogic.cake;

import domainLogic.KuchenAutomat;
import kuchen.Allergen;
import verwaltung.Hersteller;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.Date;

public abstract class CakeProductImpl implements CakeProductMutable {
    private final Collection<Allergen> allergene;
    private Hersteller hersteller;
    private final int naehrwert;
    private final Date creationDate;
    private Date inspektionsdatum;
    private final BigDecimal preis;
    private KuchenAutomat kuchenAutomat;

    public CakeProductImpl(BigDecimal preis, int naehrwert, Collection<Allergen> allergene){
        this.preis = preis;
        this.naehrwert = naehrwert;
        this.allergene = allergene;
        this.creationDate = Date.from(Instant.now());
        this.hersteller = null;
        this.inspektionsdatum = null;
    }

    @Override
    public Hersteller getHersteller() {
        return this.hersteller;
    }

    public void setHersteller(Hersteller hersteller) {
        this.hersteller = hersteller;
    }

    @Override
    public Collection<Allergen> getAllergene() {
        return this.allergene;
    }

    @Override
    public int getNaehrwert() {
        return this.naehrwert;
    }

    @Override
    public Duration getHaltbarkeit() {
        return Duration.between(creationDate.toInstant(), Instant.now());
    }

    @Override
    public BigDecimal getPreis() {
        return this.preis;
    }

    @Override
    public Date getInspektionsdatum() {
        return this.inspektionsdatum;
    }

    public void updateInspektionsdatum() {
        this.inspektionsdatum = Date.from(Instant.now());
    }

    public void setKuchenautomat(KuchenAutomat kuchenAutomat) {
        this.kuchenAutomat = kuchenAutomat;
    }

    @Override
    public int getFachnummer() {
        return this.kuchenAutomat.getIndexOfCake(this);
    }
}
