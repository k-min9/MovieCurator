from django.urls import path
from . import views

app_name = 'movies'

urlpatterns = [
    # 단일 영화, 추천, 홈
    path('', views.home),
    path('<int:movie_pk>/', views.movie_detail),
    path('<int:movie_pk>/recommend/', views.movie_recommend),

    # 영화 검색, 분류 관련
    path('search/', views.movie_search),
    path('list/', views.movie_list),

    # 평가 관련
    path('<int:movie_pk>/articles/', views.article_detail),  # 평가 상세
    path('<int:movie_pk>/articles/list/', views.article_list),  # 그 영화 관련 평가 전부
    path('<int:user_pk>/articles/curators/', views.article_curator),  # 해당 작성자가 적은 평가 3개
    path('<int:user_pk>/articles/curators/all/', views.article_curator_all),  # 해당 작성자가 적은 평가 전부, 나중에 페이지네이터, 인피니티 스크롤 등을 생각하여 분리
    path('articles/home/', views.article_home),  # 홈에서 보여줄 상위 평가
    path('<int:article_pk>/article/', views.get_article),


    # 댓글 관련
    path('<int:article_pk>/comments/', views.comment_detail),
    path('<int:article_pk>/comments/list/', views.comment_list),

    # 평가 포인트 갱신 (마일리지)
    path('donate/<int:article_pk>/', views.point_change),

    # 좋아요 관련
    path('<int:article_pk>/likes/', views.likes),
    path('likes/', views.likes_list),

]
