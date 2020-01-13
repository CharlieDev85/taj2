package com.taj.demo.service;

import com.taj.demo.model.Category;
import com.taj.demo.repository.CategoryRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.then;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CategoryServiceTest {


    @Mock
    private CategoryRepository categoryRepository;
    @InjectMocks
    private CategoryService underTest;

    @Test
    void findCategoryByCategoryName() {
        Category cats = new Category();
        cats.setCategoryName("cats");
        when(categoryRepository.findByCategoryName("cats")).thenReturn(cats);
        Category found = underTest.findCategoryByCategoryName("cats");
        assertThat(found).isNotNull();
        assertThat(found.getCategoryName()).isEqualTo("cats");
        verify(categoryRepository).findByCategoryName(anyString());

    }

    //same as the previous but BDD style
    @Test
    void findCategoryByBDDCategoryName() {
        //given
        Category cats = new Category();
        cats.setCategoryName("cats");
        given(categoryRepository.findByCategoryName("cats")).willReturn(cats);

        //when
        Category found = underTest.findCategoryByCategoryName("cats");

        //then
        assertThat(found).isNotNull();
        assertThat(found.getCategoryName()).isEqualTo("cats");
        //verify(categoryRepository).findByCategoryName(anyString());
        then(categoryRepository).should().findByCategoryName(anyString());
        then(categoryRepository).should(times(1)).findByCategoryName(anyString());
    }

    @Test
    void findAll() {
    }

    @Test
    void delete() {
        //given
        Category wifes = new Category();
        wifes.setCategoryName("wifes");
        //when
        underTest.delete(wifes);
        //then
        then(categoryRepository).should().delete(any(Category.class));
    }

    @Test
    void saveCategory() {
    }

    @Test
    void findCategoryById() {
        //given
        //when
        underTest.findCategoryById(2);
        underTest.findCategoryById(3);
        //then
        then(categoryRepository).should(times(2)).findById(anyLong());
    }

    @Test
    void testDoThrow(){
        doThrow(new RuntimeException("boom")).when(categoryRepository).delete(any());
        assertThrows(RuntimeException.class, ()-> categoryRepository.delete(new Category()));
        verify(categoryRepository).delete(any());
    }

}