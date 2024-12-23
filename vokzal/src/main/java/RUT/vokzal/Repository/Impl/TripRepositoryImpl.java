package RUT.vokzal.Repository.Impl;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import RUT.vokzal.Model.enums.StatusTrip;
import RUT.vokzal.Model.entity.Trip;
import RUT.vokzal.Repository.BaseRepository;
import RUT.vokzal.Repository.TripRepository;
import jakarta.transaction.Transactional;

@Repository
public class TripRepositoryImpl extends BaseRepository<Trip, Integer> implements TripRepository {

    public TripRepositoryImpl() {
        super(Trip.class);
    }

    @Override
    @Transactional
    public void create(Trip trip) {
        super.create(trip);
    }

    @Override
    public Trip findById(Integer id) {
        return super.findById(id);
    }

    @Override
    @Transactional
    public Trip update(Trip trip) {
        return super.update(trip);
    }

    @Override
    public List<Trip> findAll() {
        return super.findAll();
    }

    @Override
    public List<Trip> findUpcomingTripsWithTimeByStation(int stationId, LocalDate nowDate, LocalTime nowTime) {
    return entityManager.createQuery(
            "SELECT t " +
            "FROM Trip t JOIN t.route r " +
            "WHERE r.depPlId.vokzalId.id = :stationId " +
            "AND r.depPlId.vokzalId.del <> true " +
            "AND r.arrPlId.vokzalId.del <> true " +
            "AND t.statusTrip <> :cancelled " +
            "AND (t.dateDep > :nowDate OR (t.dateDep = :nowDate AND r.timeDep >= :nowTime)) " +
            "ORDER BY t.dateDep, r.timeDep", 
            Trip.class)
            .setParameter("stationId", stationId)
            .setParameter("cancelled", StatusTrip.CANCELLED)
            .setParameter("nowDate", nowDate)
            .setParameter("nowTime", nowTime)
//            .setMaxResults(5)
            .getResultList();
    }

    @Override
    public List<Trip> findCanceledTrips(String vokzalName, LocalDate nowDate) {
        return entityManager.createQuery(
                        "SELECT t FROM Trip t " +
                                "JOIN t.route r " +
                                "JOIN r.depPlId p " +
                                "JOIN p.vokzalId v " +
                                "WHERE t.statusTrip = :cancelled AND v.name = :vokzalName " +
                                "AND r.depPlId.vokzalId.del <> true " +
                                "AND r.arrPlId.vokzalId.del <> true " +
                                "AND t.dateDep >= :nowDate", Trip.class)
                .setParameter("cancelled", StatusTrip.CANCELLED)
                .setParameter("vokzalName", vokzalName)
                .setParameter("nowDate", nowDate)
                .getResultList();
    }

    @Override
    public List<Trip> findAlternativeTrips(int routeId, LocalDate nowDate) {
    return entityManager.createQuery(
            "SELECT t FROM Trip t " +
            "WHERE t.route.id = :routeId " +
            "AND t.route.depPlId.vokzalId.del <> true " +
            "AND t.route.arrPlId.vokzalId.del <> true " +
            "AND t.statusTrip <> :cancelled AND t.statusTrip <> :completed " +
            "AND t.dateDep > :nowDate " +
            "ORDER BY t.dateDep ASC", Trip.class)
            .setParameter("routeId", routeId)
            .setParameter("cancelled", StatusTrip.CANCELLED)
            .setParameter("completed", StatusTrip.COMPLETED)
            .setParameter("nowDate", nowDate)
            .setMaxResults(1)
            .getResultList();
    }

    @Override
    public List<Trip> findTop5Trips() {
        return entityManager.createQuery(
                        "SELECT t FROM Trip t " +
                                "JOIN t.route r " +
                                "WHERE t.dateDep IS NOT NULL AND t.dateArr IS NOT NULL " +
                                "AND t.statusTrip = :scheduled " +
                                "AND r.depPlId.vokzalId.del <> true " +
                                "AND r.arrPlId.vokzalId.del <> true ",
                        Trip.class)
                .setParameter("scheduled", StatusTrip.SCHEDULED)
                .getResultList();
    }

    @Override
    public Page<Trip> findAll(Pageable pageable) {
        List<Trip> trips = entityManager.createQuery("FROM Trip t ORDER BY t.statusTrip ASC", Trip.class)
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery("SELECT COUNT(t) FROM Trip t", Long.class)
                .getSingleResult();

        return new PageImpl<>(trips, pageable, total);
    }

    @Override
    public Page<Trip> findByStatusContainingIgnoreCase(String searchTerm, Pageable pageable) {
        List<Trip> trips = entityManager.createQuery(
                        "FROM Trip t WHERE LOWER(t.statusTrip) LIKE LOWER(:status)", Trip.class)
                .setParameter("status", "%" + searchTerm + "%")
                .setFirstResult((int) pageable.getOffset())
                .setMaxResults(pageable.getPageSize())
                .getResultList();

        long total = entityManager.createQuery(
                        "SELECT COUNT(t) FROM Trip t WHERE LOWER(t.statusTrip) LIKE LOWER(:status)", Long.class)
                .setParameter("status", "%" + searchTerm + "%")
                .getSingleResult();

        return new PageImpl<>(trips, pageable, total);
    }
}