from django.urls import path
from .views import CategoriaListView, FilmeListView, VotoCreateView
from rest_framework_simplejwt.views import TokenObtainPairView, TokenRefreshView

urlpatterns = [
    path('categorias/', CategoriaListView.as_view(), name='lista_categorias'),
    path('filmes/', FilmeListView.as_view(), name='lista_filmes'),
    path('votos/', VotoCreateView.as_view(), name='registrar_voto'),
    path('token/', TokenObtainPairView.as_view(), name='token_obter'),
    path('token/refresh/', TokenRefreshView.as_view(), name='token_refresh'),
]
