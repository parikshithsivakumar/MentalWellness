package com.enotes.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import com.enotes.entity.Notes;
import com.enotes.entity.UserDtls;
import com.enotes.entity.Questionnaire;
import com.enotes.repository.NotesRepository;
import com.enotes.repository.UserRepository;
import com.enotes.repository.QuestionnaireRepository;



@Controller
@SessionAttributes({ "questionIndex", "totalScore" })
@RequestMapping("/user")
public class UserController {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private NotesRepository notesRepository;
	
	@Autowired
	private QuestionnaireRepository questionnaireRepository;

	@ModelAttribute
	public void addCommnData(Principal p, Model m) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);
		m.addAttribute("user", user);
	}

	@GetMapping("/main")
	public String home(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);
		
		// Get note count
		List<Notes> allNotes = notesRepository.findByUserDtlsId(user.getId());
		int noteCount = allNotes.size();
		
		// Get latest wellness score
		Optional<Questionnaire> latestQuestionnaire = questionnaireRepository.findFirstByUserDtlsIdOrderByCreatedAtDesc(user.getId());
		int wellnessScore = 0;
		if (latestQuestionnaire.isPresent()) {
			wellnessScore = latestQuestionnaire.get().getWellnessScore();
		}
		
		m.addAttribute("noteCount", noteCount);
		m.addAttribute("wellnessScore", wellnessScore);
		
		return "user/main";
	}



	@GetMapping("/breathbox")
	public String breathbox() {
		return "user/BreathBox";
	}

	@GetMapping("/timer")
	public String timer() {
		return "user/Timer";
	}


	@GetMapping("/addNotes")
	public String addNotes() {
		return "user/add_notes";
	}
	

	

	@GetMapping("/viewNotes/{page}")
	public String viewNotes(@PathVariable int page, Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);

		List<Notes> allNotes = notesRepository.findByUserDtlsId(user.getId());
		
		// Reverse to show latest first
		Collections.reverse(allNotes);
		
		// Simple pagination
		int pageSize = 5;
		int totalPages = (int) Math.ceil((double) allNotes.size() / pageSize);
		int start = page * pageSize;
		int end = Math.min(start + pageSize, allNotes.size());
		
		List<Notes> pageNotes = allNotes.subList(start, end);

		m.addAttribute("pageNo", page);
		m.addAttribute("totalPage", totalPages);
		m.addAttribute("Notes", pageNotes);
		m.addAttribute("totalElement", allNotes.size());

		return "user/view_notes";
	}

	@GetMapping("/editNotes/{id}")
	public String editNotes(@PathVariable String id, Model m) {
		Optional<Notes> n = notesRepository.findById(id);
		if (n.isPresent()) {
			Notes notes = n.get();
			m.addAttribute("notes", notes);
		}
		return "user/edit_notes";
	}

	@PostMapping("/updateNotes")
	public String updateNotes(@ModelAttribute Notes notes, HttpSession session, Principal p) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);

		notes.setUserDtls(user);

		Notes updateNotes = notesRepository.save(notes);

		if (updateNotes != null) {
			session.setAttribute("msg", "Notes Update Sucessfully");
		} else {
			session.setAttribute("msg", "Something wrong on server");
		}

		System.out.println(notes);

		return "redirect:/user/viewNotes/0";
	}

	@GetMapping("/deleteNotes/{id}")
	public String deleteNotes(@PathVariable String id, HttpSession session) {
		Optional<Notes> notes = notesRepository.findById(id);
		if (notes.isPresent()) {
			notesRepository.delete(notes.get());
			session.setAttribute("msg", "Notes Delete Successfully");
		}
		return "redirect:/user/viewNotes/0";
	}

	@GetMapping("/viewProfile")
	public String viewProfile(Model m, Principal p) {
		String email = p.getName();
		UserDtls user = userRepository.findByEmail(email);
		
		// Get note count
		List<Notes> allNotes = notesRepository.findByUserDtlsId(user.getId());
		int noteCount = allNotes.size();
		
		// Get latest wellness score
		Optional<Questionnaire> latestQuestionnaire = questionnaireRepository.findFirstByUserDtlsIdOrderByCreatedAtDesc(user.getId());
		int wellnessScore = 0;
		if (latestQuestionnaire.isPresent()) {
			wellnessScore = latestQuestionnaire.get().getWellnessScore();
		}
		
		m.addAttribute("noteCount", noteCount);
		m.addAttribute("wellnessScore", wellnessScore);
		
		return "user/view_profile";
	}

	@PostMapping("/saveNotes")
	public String saveNotes(@ModelAttribute Notes notes, HttpSession session, Principal p) {
		String email = p.getName();
		UserDtls u = userRepository.findByEmail(email);
		notes.setUserDtls(u);

		Notes n = notesRepository.save(notes);

		if (n != null) {
			session.setAttribute("msg", "Notes Added Sucessfully");
		} else {
			session.setAttribute("msg", "Something wrong on server");
		}

		return "redirect:/user/addNotes";
	}
	
	@PostMapping("/updateUser")
	public String updateUser(@ModelAttribute UserDtls user,HttpSession session,Model m)
	{
		Optional<UserDtls> oldUser=userRepository.findById(user.getId());
		
		if(oldUser.isPresent())
		{
			user.setPassword(oldUser.get().getPassword());
			user.setRole(oldUser.get().getRole());
			user.setEmail(oldUser.get().getEmail());
			
			UserDtls updateUser=userRepository.save(user);
			if(updateUser!=null)
			{
				m.addAttribute("user",updateUser);
				session.setAttribute("msg", "Profile Update Sucessfully..");
			}
			
		}
		
		
		return "redirect:/user/viewProfile";
	}

	private static final List<String> questions = new ArrayList<>();

    // Static block to initialize questions
    static {
        questions.add("How would you rate your overall mood today?");
        questions.add("How well do you feel you have been managing stress recently?");
		questions.add("On a scale from 1 to 10, how would you rate your current level of anxiety?");
        questions.add("How many hours of sleep do you typically get per night?");
        questions.add("How often do you engage in activities that you find enjoyable or relaxing?");
        questions.add("Are you able to concentrate and focus on tasks easily?");
        questions.add("How would you describe your current level of energy and motivation?");
        questions.add("Do you have a support system (friends, family, etc.) that you can rely on?");
        questions.add("How often do you experience feelings of loneliness?");
        questions.add("Are you satisfied with your work-life balance?");
        questions.add("How would you rate your ability to cope with life's challenges?");
        questions.add("Do you engage in regular physical exercise?");
        questions.add("How often do you take breaks or practice mindfulness during the day?");
        questions.add("Are you comfortable expressing your thoughts and feelings to others?");
        questions.add("How do you handle setbacks or disappointments?");
    }

    @GetMapping("/questionnaire")
    public String showQuestionnaire(Model model) {
        Integer questionIndex = (Integer) model.getAttribute("questionIndex");

        // Check if questionIndex is null and initialize it with the default value (0 in this case)
        if (questionIndex == null) {
            questionIndex = 0;
            model.addAttribute("questionIndex", questionIndex);
            model.addAttribute("totalScore", 0);
        }

        // Get the current question using the getQuestion method
        String currentQuestion = getQuestion(questionIndex);
        
        // Calculate progress
        int totalQuestions = questions.size();
        int progressPercentage = ((questionIndex + 1) * 100) / totalQuestions;
        
        // Update the model attributes
        model.addAttribute("currentQuestion", currentQuestion);
        model.addAttribute("questionNumber", questionIndex + 1);
        model.addAttribute("totalQuestions", totalQuestions);
        model.addAttribute("progressPercentage", progressPercentage);

        return "user/questionnaire";
    }

    @PostMapping("/questionnaire")
    public String processAnswer(@RequestParam int answer, Model model, Principal p) {
        Integer questionIndex = (Integer) model.getAttribute("questionIndex");
        Integer totalScore = (Integer) model.getAttribute("totalScore");

        if (questionIndex == null) {
            questionIndex = 0;
            model.addAttribute("questionIndex", questionIndex);
        }

        if (totalScore == null) {
            totalScore = 0;
            model.addAttribute("totalScore", totalScore);
        }

        // Accept numeric answers (1-5)
        if (answer >= 1 && answer <= 5) {
            totalScore += answer;
        } else {
            System.out.println("Invalid answer value. Answer must be between 1 and 5.");
        }

        questionIndex++;

        if (questionIndex < questions.size()) {
            model.addAttribute("questionIndex", questionIndex);
            model.addAttribute("totalScore", totalScore);
            return "redirect:/user/questionnaire";
        } else {
            // Calculate wellness score: (totalScore / (numberOfQuestions * 5)) * 100, then invert to get wellness score
            int numberOfQuestions = questions.size();
            int maxPossibleScore = numberOfQuestions * 5;
            int stressLevel = (totalScore * 100) / maxPossibleScore; // Lower is better (stress level)
            int wellnessScore = 100 - stressLevel; // Wellness is inverse of stress
            
            // Save questionnaire to MongoDB
            String email = p.getName();
            UserDtls user = userRepository.findByEmail(email);
            
            String assessment = determineResultMessage(totalScore);
            
            Questionnaire questionnaire = new Questionnaire(user, totalScore, wellnessScore, assessment);
            questionnaireRepository.save(questionnaire);
            
            // Store in model for results page
            model.addAttribute("totalScore", totalScore);
            model.addAttribute("wellnessScore", wellnessScore);
            
            return "redirect:/user/results";
        }
    }

    @GetMapping("/results")
    public String showResults(Model model, SessionStatus sessionStatus, Principal p) {
        Integer totalScore = (Integer) model.getAttribute("totalScore");
        Integer wellnessScore = (Integer) model.getAttribute("wellnessScore");
        
        // If not in model, try to get from database
        if (totalScore == null || wellnessScore == null) {
            String email = p.getName();
            UserDtls user = userRepository.findByEmail(email);
            Optional<Questionnaire> latestResult = questionnaireRepository.findFirstByUserDtlsIdOrderByCreatedAtDesc(user.getId());
            
            if (latestResult.isPresent()) {
                Questionnaire questionnaire = latestResult.get();
                totalScore = questionnaire.getTotalScore();
                wellnessScore = questionnaire.getWellnessScore();
                model.addAttribute("totalScore", totalScore);
                model.addAttribute("wellnessScore", wellnessScore);
            }
        }
        
        String resultMessage = determineResultMessage(totalScore);
        model.addAttribute("resultMessage", resultMessage);
        model.addAttribute("wellnessScore", wellnessScore);

        sessionStatus.setComplete();

        return "user/results";
    }

    private String determineResultMessage(int totalScore) {
        int numberOfQuestions = questions.size();
        int maxScore = numberOfQuestions * 5;
        int avgScore = totalScore / numberOfQuestions;
        
        if (avgScore <= 2) {
            return "Your mental well-being is in an excellent state. You're managing stress very well!";
        } else if (avgScore <= 3) {
            return "Your mental health is good overall. Keep up the positive practices!";
        } else if (avgScore <= 4) {
            return "Moderate stress detected. Consider taking more time for relaxation and self-care.";
        } else {
            return "High stress levels identified. Please consider seeking support and implementing stress management techniques.";
        }
    }

    private static String getQuestion(int index) {
        if (index >= 0 && index < questions.size()) {
            return questions.get(index);
        } else {
            return "Invalid question index.";
        }
    }

	
}
