package kopo.poly.repository;

import kopo.poly.repository.entity.CenterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface CenterRepository extends JpaRepository<CenterEntity,String> {

    CenterEntity findByCenterName (String centerName);

    int deleteByCenterSeq(long centerSeq);

}