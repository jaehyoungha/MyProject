package kopo.poly.repository;

import kopo.poly.repository.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity ,String> {


    /**
     * 회원가입
     * */
    //회원 존재여부 확인
    //Optional은 객체에 값이 nullpoint 에러에 대응하기 위해
    // == SELECT * FROM USER_INFO WHERE USER_ID ='FLORA'
    Optional<UserEntity> findByUserId(String userId);


    //닉네임 중복조회
    Optional<UserEntity> findByUserNickName(String userNickName);

    //이메일 중복조회
    Optional<UserEntity> findByUserEmail(String userEmail);

    //아이디 찾기
    UserEntity findByUserNameAndUserEmail(String userName, String userEmail);


}