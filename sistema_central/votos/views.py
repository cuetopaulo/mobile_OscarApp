from django.shortcuts import render

from rest_framework import generics
from rest_framework.permissions import IsAuthenticated
from rest_framework_simplejwt.views import TokenObtainPairView
from .models import Categoria, Filme, Voto
from .serializers import CategoriaSerializer, FilmeSerializer, VotoSerializer
import random
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework import status

class CategoriaListView(generics.ListAPIView):
    queryset = Categoria.objects.all()
    serializer_class = CategoriaSerializer

class FilmeListView(generics.ListAPIView):
    queryset = Filme.objects.all()
    serializer_class = FilmeSerializer

class VotoCreateView(generics.CreateAPIView):
    queryset = Voto.objects.all()
    serializer_class = VotoSerializer
    permission_classes = [IsAuthenticated]

class RandomTokenView(APIView):
    def post(self, request):
        # Gera um número aleatório entre 1 e 100
        token = random.randint(1, 100)
        return Response({'token': token}, status=status.HTTP_200_OK)
