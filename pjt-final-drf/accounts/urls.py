from django.urls import path
from rest_framework_jwt.views import obtain_jwt_token
from . import views

app_name = 'accounts'

urlpatterns = [
    path('<str:username>/get_user_info/', views.get_user_info),
    path('api-token-auth/', obtain_jwt_token),
    path('signup/', views.signup),
    path('profile/', views.my_profile_detail),
    path('profile/<int:user_pk>/', views.profile_detail),

    # 마일리지 충전, 후원받기
    path('mileage/', views.mileage_change),
    path('donate/<int:user_pk>/', views.donate),

    # 큐레이터 이름으로 검색
    path('search/', views.curator_search),
    # 큐레이터 번호로 단일 상세
    path('curators/<int:user_pk>/', views.curator_detail),
    # 후원한 큐레이터 리스트 받기
    path('curators/likes/', views.likes_list_curator),
]
