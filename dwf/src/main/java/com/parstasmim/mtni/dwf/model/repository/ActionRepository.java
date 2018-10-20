package com.parstasmim.mtni.dwf.model.repository;

import com.parstasmim.mtni.dwf.model.entity.Action;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ActionRepository extends CrudRepository<Action, Long> {

    List<Action> findByAccessibility(boolean accessibility);

    @Query("select a from Action a where (a.requestType like '[?1%' or a.requestType like '%?1%' or a.requestType like '%?1]') and (a.url like '[?2%' or a.url like '%?2%' or a.url like '%?2]')")
    Action findByRequestTypeAndUrl(String requestType, String url);

//    @Query("select count(a) from Action a where (a.requestType like '[?1%' or a.requestType like '%?1%' or a.requestType like '%?1]') and (a.url like '[?2%' or a.url like '%?2%' or a.url like '%?2]') and (a.needAuthentication=?3)")
//    int countByRequestTypeAndUrlAndNeedAuthentication(String requestType, String url, int isNeedAuthentication);

}