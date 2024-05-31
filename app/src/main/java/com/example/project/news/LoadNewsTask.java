package com.example.project.news;

import android.os.AsyncTask;

import com.example.project.news.News;
import com.example.project.news.NewsFragment;

import java.util.ArrayList;
import java.util.List;

public class LoadNewsTask extends AsyncTask<Void, Void, List<News>> {
    private NewsFragment mNewsFragment;

    public LoadNewsTask(NewsFragment newsFragment) {
        mNewsFragment = newsFragment;
    }

    @Override
    protected List<News> doInBackground(Void... voids) {
        List<News> news = new ArrayList<>();
        // TODO: Загрузить новости в фоновом потоке
        // Например, вы можете использовать веб-скрейпинг или API, предоставляемое сайтом
        // В этом примере, я использовал статические данные для простоты
        news.add(new News("BMW обновил 3-ю серию", "https://news.drom.ru/BMW-97062.html"));
        news.add(new News("Mercedes-AMG представил коллекционный родстер PureSpeed", "https://news.drom.ru/Mercedes-AMG-PureSpeed-97016.html"));
        news.add(new News("Audi выпустила кроссовер новой эры","https://news.drom.ru/Audi-96513.html"));
        news.add(new News("Porsche 911 впервые стал гибридом","https://news.drom.ru/Porsche-911-97059.html"));
        news.add(new News("BMW представил уникальный M8","https://news.drom.ru/BMW-M8-97030.html"));
        news.add(new News("Kia представила электрокроссовер для массового потребителя","https://news.drom.ru/Kia-97017.html"));
        news.add(new News("Honda обновила Civic","https://news.drom.ru/Honda-Civic-96985.html"));
        news.add(new News("Любопытные объявления на Дроме: «бусик» под старый Volkswagen","https://news.drom.ru/Volkswagen-96976.html"));
        news.add(new News("В Китае представили улучшенный клон Tesla Model Y","https://news.drom.ru/Tesla-Model-Y-96948.html"));
        news.add(new News("Kia обновил электрокроссовер EV6","https://news.drom.ru/Kia-EV6-96929.html"));
        return news;
    }

    @Override
    protected void onPostExecute(List<News> news) {
        super.onPostExecute(news);
        // TODO: Обновить UI в основном потоке
        // В этом примере, я обновляю UI вручную, но вы можете использовать библиотеки, такие как LiveData или RxJava, для автоматического обновления UI
        mNewsFragment.updateNews(news);
    }
}

