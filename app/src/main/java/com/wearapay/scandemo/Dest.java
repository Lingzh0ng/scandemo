package com.wearapay.scandemo;

import com.wearapay.domain.repository.IUserRepository;
import javax.inject.Inject;

/**
 * Created by lyz on 2017/10/12.
 */

public class Dest {
  IUserRepository repository;

  @Inject public Dest(IUserRepository repository) {
    this.repository = repository;
  }

  public String show(String name, String pwd) {
    return repository.test(name, pwd);
  }
}
