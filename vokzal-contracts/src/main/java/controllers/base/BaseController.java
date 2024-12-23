package controllers.base;

import dto.base.BaseViewModel;

public interface BaseController {
    /**
     * Создает базовую модель представления.
     * @param title заголовок страницы
     * @return базовая модель с общими данными
     */
    BaseViewModel createBaseViewModel(String title);
}