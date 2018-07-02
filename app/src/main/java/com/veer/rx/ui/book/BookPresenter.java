package com.veer.rx.ui.book;


import com.veer.rx.base.BasePresenter;
import com.veer.rx.model.BookModel;
import com.veer.rx.net.RetrofitHelper;
import com.veer.rx.net.RxSchedulers;

import io.reactivex.functions.Consumer;

/**
 * 描述
 *
 * @author Veer
 * @email 276412667@qq.com
 * @date 18/7/2
 */

public class BookPresenter extends BasePresenter<BookContract.View> implements BookContract.Presenter{

    @Override
    public void getBook(String p, String tag, String start, String end) {
        RetrofitHelper.getInstance().getServer()
                .getBooks(p,tag,start,end)
                .compose(RxSchedulers.<BookModel>applySchedulers())
                .compose(mView.<BookModel>bindToLife())
                .subscribe(new Consumer<BookModel>() {
                    @Override
                    public void accept(BookModel bookModel) throws Exception {
                     mView.setBook(bookModel);
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        mView.showFailed("error");
                    }
                });
    }
}