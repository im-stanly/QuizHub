import { Button } from '@mui/material'

interface QuizButtonProps {
    buttonID: number;
    answerString: string;
    correctAnswer: number | undefined;
    answer: number | null;
    handleButtonClick: (answer: number) => void;
}

const QuizButton: React.FC<QuizButtonProps> = ({
    buttonID,
    answerString,
    correctAnswer,
    answer,
    handleButtonClick,
  }) => {
    return (
      <Button
        variant="contained"
        onClick={() => handleButtonClick(buttonID)} 
        disabled={answer !== null} 
        sx={{
          width: "400px",
          height: "200px",
          backgroundColor: answer === null ? "#636363" : (buttonID === correctAnswer ? "green" : "#636363"),
          color: "white",
          "&.Mui-disabled": {
            backgroundColor: (correctAnswer === undefined ? (buttonID === answer ? "orange" : "#636363") : 
                                                            (buttonID === correctAnswer ? "green" : "red")),
            color: "white",
          },
          "&:hover": {
            backgroundColor: "#363636",
          },
        }}
      >
        {answerString}
      </Button>
    );
  };

  export default QuizButton;