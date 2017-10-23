package com.wearapay.scandemo;

import com.wearapay.domain.repository.ILocalRepository;
import com.wearapay.domain.repository.IUserRepository;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/12.
 */

public class Dest {
  IUserRepository repository;
  ILocalRepository localRepository;

  @Inject public Dest(IUserRepository repository, ILocalRepository localRepository) {
    this.repository = repository;
    this.localRepository = localRepository;
  }

  public String show(String name, String pwd) {
    return repository.test(name, pwd);
  }

  public boolean getLoginStatus(){
    return localRepository.getLoginStatus();
  }
}
