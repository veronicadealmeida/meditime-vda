package com.medi.service;

import com.medi.model.Horario;
import com.medi.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class WhatsAppNotificationService {

    private final HorarioRepository horarioRepository;
    private final RestTemplate restTemplate;
    private static final String API_URL = "https://api.callmebot.com/whatsapp.php";

    @Autowired
    public WhatsAppNotificationService(HorarioRepository horarioRepository, RestTemplate restTemplate) {
        this.horarioRepository = horarioRepository;
        this.restTemplate = restTemplate;
    }

    @Scheduled(fixedRate = 60000) // Executa a cada 1 minuto (60000 ms)
    public void verificarEEnviarMensagens() {
        LocalTime agora = LocalTime.now().withSecond(0).withNano(0);
        System.out.println("🔍 Verificando horários às: " + agora);

        List<Horario> horarios = horarioRepository.findAll();

        for (Horario horario : horarios) {
            if (horario.getMedicamentoId() == null) {
                System.err.println("❌ Erro: Medicamento não encontrado para o horário ID: " + horario.getId());
                continue;
            }

            try {
                // Converte a hora do medicamento para LocalTime
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
                LocalTime horaMedicamento = LocalTime.parse(horario.getMedicamentoId().getHora(), formatter);

                boolean deveEnviar = "S".equalsIgnoreCase(horario.getMedicamentoId().getEnviaLembrete());

                System.out.println("🕒 Medicamento: " + horario.getMedicamentoId().getNome() +
                        " - Hora: " + horaMedicamento +
                        " - Envia Lembrete: " + (deveEnviar ? "✅ Sim" : "❌ Não"));

                // Compara a hora do medicamento com a hora atual
                if (horaMedicamento.equals(agora) && deveEnviar) {
                    enviarMensagem(horario);
                }
            } catch (Exception e) {
                System.err.println("⚠️ Erro ao processar horário ID " + horario.getId() + ": " + e.getMessage());
            }
        }
    }

    private void enviarMensagem(Horario horario) {
        if (horario.getUsuarioId() == null) {
            System.err.println("❌ Erro: Usuário não encontrado para o horário ID: " + horario.getId());
            return;
        }

        String telefone = horario.getUsuarioId().getTelefone();
        String apiKey = horario.getUsuarioId().getMessageKey(); // Obtendo a API Key do usuário

        if (apiKey == null || apiKey.isEmpty()) {
            System.err.println("❌ Erro: API Key do usuário está vazia ou nula!");
            return;
        }

        String mensagem = "💊 *Lembrete de Medicamento!*\n" +
                "📌 *Medicamento:* " + horario.getMedicamentoId().getNome() + "\n" +
                "💉 *Dosagem:* " + horario.getMedicamentoId().getDosagem() + "\n" +
                "⏰ *Hora:* " + horario.getMedicamentoId().getHora() + "\n" +
                "🔔 *Não se esqueça de tomar seu remédio!*";

        String url = API_URL + "?phone=" + telefone + "&text=" + mensagem + "&apikey=" + apiKey;

        System.out.println("📤 Enviando mensagem para: " + telefone);
        System.out.println("🔗 URL: " + url);

        try {
            ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
            System.out.println("✅ Resposta da API: " + response.getBody());
        } catch (Exception e) {
            System.err.println("❌ Erro ao enviar mensagem: " + e.getMessage());
        }
    }
}
