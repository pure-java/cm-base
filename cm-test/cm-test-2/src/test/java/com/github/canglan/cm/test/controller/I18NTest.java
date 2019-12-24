package com.github.canglan.cm.test.controller;

import com.github.canglan.cm.test.Test2Application;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author bairitan
 * @date 2019/12/24
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {Test2Application.class})
public class I18NTest {

  @Autowired
  private MessageSource messageSource;

  @Test
  public void getMessage() {
    System.out.println(messageSource.getMessage("500", null, LocaleContextHolder.getLocale()));
  }
}
