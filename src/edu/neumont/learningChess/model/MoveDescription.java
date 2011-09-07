package edu.neumont.learningChess.model;

	
	public class MoveDescription {
		private Move move;
		private ChessPiece movingPiece;
		private Team takenTeam;
		private ChessPiece takenPiece;
		private ChessPiece promotionPiece = null;
		
		public MoveDescription(Move move, ChessPiece movingPiece, Team takenTeam, ChessPiece takenPiece) {
			this.move = move;
			this.movingPiece = movingPiece;
			this.takenTeam = takenTeam;
			this.takenPiece = takenPiece;
		}
		
		public Move getMove() {
			return move;
		}
		
		public ChessPiece getMovingPiece() {
			return movingPiece;
		}
		
		public Team getTakenTeam() {
			return takenTeam;
		}
		
		public ChessPiece getTakenPiece() {
			return takenPiece;
		}
		
		public void setTakenPiece(ChessPiece piece) {
			takenPiece = piece;
			takenTeam = piece.getTeam();
		}

		public ChessPiece getPromotionPiece() {
			return promotionPiece;
		}

		public void setPromotionPiece(ChessPiece promotionPiece) {
			this.promotionPiece = promotionPiece;
		}

	}


