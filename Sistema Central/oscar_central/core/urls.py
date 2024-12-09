from django.urls import path
from .views import LoginView, VotoView

urlpatterns = [
    path('auth/login/', LoginView.as_view(), name='login'),
    path('voto/', VotoView.as_view(), name='voto'),
]
