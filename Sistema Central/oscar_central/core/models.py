import random

from django.db import models
from django.contrib.auth.models import User

class Categoria(models.Model):
    nome = models.CharField(max_length=100)

    def __str__(self):
        return self.nome

class Filme(models.Model):
    nome = models.CharField(max_length=100)
    categoria = models.ForeignKey(Categoria, on_delete=models.CASCADE)

    def __str__(self):
        return self.nome

class Voto(models.Model):
    usuario = models.ForeignKey(User, on_delete=models.CASCADE)
    filme = models.ForeignKey(Filme, on_delete=models.CASCADE)
    data_voto = models.DateTimeField(auto_now_add=True)

class Token(models.Model):
    usuario = models.OneToOneField(User, on_delete=models.CASCADE)
    token = models.CharField(max_length=255)
    data_expiracao = models.DateTimeField()
    
class UserProfile(models.Model):
    user = models.OneToOneField(User, on_delete=models.CASCADE)
    voting_token = models.IntegerField(unique=True)  # Armazenar o token de votação aqui

    def save(self, *args, **kwargs):
        if not self.voting_token:
            self.voting_token = random.randint(0, 100)  # Gera o token de votação entre 0 e 100
        super(UserProfile, self).save(*args, **kwargs)

    def __str__(self):
        return f'{self.user.username} - Token: {self.voting_token}'

