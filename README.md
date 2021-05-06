# NewsApiCleanArchitecture

<img align="left" src="https://github.com/buraktemzc/NewsApiCleanArchitecture/raw/master/assets/news_list.png" width="200">
<img align="center" src="https://github.com/buraktemzc/NewsApiCleanArchitecture/raw/master/assets/calendar_select.png" width="200">
<img align="center" src="https://github.com/buraktemzc/NewsApiCleanArchitecture/raw/master/assets/news_detail.png" width="200">

In this project, I used Clean Architecture with MVVM using Dagger-Hilt, Coroutines, Navigation Component (with safe args), Retrofit and Glide. On the other hand, I used JUnit, MockWebServer and Truth
libraries to verify the accuracy of the requests. 

#### The app has below packages:
1. **data**: This package contains models, datasources and endpoint interface for news api.
2. **domain**: This package contains usecases and repositories for sending requests to fill models and serve data to presentation layer.
3. **presentation**: All UI components and ViewModels are located in this package.
4. **common**: Common classes used in many parts of the application are under this package.

