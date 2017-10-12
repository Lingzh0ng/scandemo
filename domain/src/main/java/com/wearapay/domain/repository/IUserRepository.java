package com.wearapay.domain.repository;

import com.wearapay.base.net.IRepository;
import io.reactivex.Observable;

/**
 * Created by lyz on 2017/10/12.
 */

public interface IUserRepository extends IRepository {
  Observable<String> login(String name, String pwd);

  String test(String name, String pwd);
}
