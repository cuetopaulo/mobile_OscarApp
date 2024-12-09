from django.shortcuts import render


from rest_framework_simplejwt.tokens import RefreshToken
from rest_framework.views import APIView
from rest_framework.response import Response
from rest_framework.permissions import IsAuthenticated
from django.contrib.auth import authenticate
from rest_framework.authtoken.models import Token
from .models import Voto, Filme, Categoria
from .models import UserProfile  # Importar o modelo UserProfile

from rest_framework import status
from rest_framework.decorators import api_view
from django.contrib.auth.models import User

def home(request):
    return render(request, 'home.html')  # ou uma resposta simples

class LoginView(APIView):
    def post(self, request):
        email = request.data.get('email')
        senha = request.data.get('senha')
        user = authenticate(username=email, password=senha)
        if user:
            token, _ = Token.objects.get_or_create(user=user)
            return Response({"token": token.key})
        return Response({"error": "Credenciais inválidas"}, status=400)

class VotoView(APIView):
    permission_classes = [IsAuthenticated]

    def post(self, request):
        filme_id = request.data.get('id_filme')
        filme = Filme.objects.get(id=filme_id)
        Voto.objects.create(usuario=request.user, filme=filme)
        return Response({"mensagem": "Voto registrado com sucesso"})
        
@api_view(['POST'])
def register_user(request):
    username = request.data.get('username')
    password = request.data.get('password')

    if username is None or password is None:
        return Response({"error": "Username and password are required"}, status=status.HTTP_400_BAD_REQUEST)

    # Cria o usuário
    user = User.objects.create_user(username=username, password=password)

    # Cria o perfil de usuário com o token de votação
    user_profile = UserProfile.objects.create(user=user)

    # Retorna o token de votação
    return Response({
        "message": "User created successfully",
        "voting_token": user_profile.voting_token,
    }, status=status.HTTP_201_CREATED)
    

@api_view(['POST'])
def login_user(request):
    # Pega os dados de login do corpo da requisição
    username = request.data.get('username')
    password = request.data.get('password')

    # Verifica se ambos os campos de username e password foram fornecidos
    if username is None or password is None:
        return Response({"error": "Username and password are required"}, status=400)

    # Autentica o usuário com as credenciais fornecidas
    user = authenticate(username=username, password=password)

    # Se o usuário for autenticado, gera o JWT e o token de votação
    if user is not None:
        try:
            # Assume que o modelo UserProfile contém o campo 'voting_token'
            user_profile = UserProfile.objects.get(user=user)
            voting_token = user_profile.voting_token  # Token de votação do usuário

            return Response({
                'voting_token': voting_token,  # Retorna apenas o token de votação
            })
        except UserProfile.DoesNotExist:
            return Response({"error": "User profile not found"}, status=404)
    else:
        return Response({"error": "Invalid credentials"}, status=400)
