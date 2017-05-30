import SpriteKit
import GameplayKit

class GameScene: SKScene {
    
    var circulo = SKShapeNode(circleOfRadius: 50);
    var caixa = SKShapeNode(rect: CGRect(x: 0, y: 0, width: 50, height: 50));
    var placar = SKLabelNode(text: "Placa:0");
    var noPath = SKShapeNode()
    var chao:SKShapeNode!
    var personagem = SKSpriteNode(imageNamed: "mark-sprite-Walk3");
    
    
    override func didMove(to view: SKView) {
        
        // circulo
        circulo.position = CGPoint(x: 200, y: 200);
        circulo.fillColor = #colorLiteral(red: 0.319275558, green: 0.8162181973, blue: 0.859744966, alpha: 1);
        addChild(circulo);
        
        //caixa
        caixa.position = CGPoint(x: -25, y: -25);
        caixa.fillColor = #colorLiteral(red: 0.8078431487, green: 0.02745098062, blue: 0.3333333433, alpha: 1);
        addChild(caixa);
        
        //placar
        placar.position = CGPoint(x: -(frame.width/2) + 100, y: -frame.height/2 + 50);
        addChild(placar);
        
        //paths
        let path = CGMutablePath();
        path.move(to: CGPoint(x: -50, y:-50));
        path.addLine(to: CGPoint(x: -175,y: -175));
        path.addLine(to: CGPoint(x: -200, y:-50));
        path.closeSubpath();
        
        noPath = SKShapeNode(path: path);
        noPath.fillColor = #colorLiteral(red: 0.4666666687, green: 0.7647058964, blue: 0.2666666806, alpha: 1);
        noPath.position = CGPoint(x: 0, y: 0);
        addChild(noPath);
        
        //chao
        chao = SKShapeNode(rect: CGRect(x: -frame.width * 0.5, y: -frame.height * 0.5, width: frame.width, height: 200))
        chao.fillColor = #colorLiteral(red: 0.1960784346, green: 0.3411764801, blue: 0.1019607857, alpha: 1)
        addChild(chao);
        
        
        // fades
        caixa.alpha = 0;
        let fadeIn = SKAction.fadeAlpha(to: 1.0, duration: 2);
//        caixa.run(fadeIn);
        
        let fadeOut = SKAction.fadeAlpha(to: 0.0, duration: 2);
        circulo.run(fadeOut);
        
        let seq01 = SKAction.sequence([fadeOut, fadeIn]);
//        caixa.run(seq01);
    
        
        caixa.run(SKAction.repeat(seq01, count: 10));
        circulo.run(SKAction.repeatForever(seq01));
        
        personagem.position = CGPoint(x: 0, y: 0);
        personagem.setScale(0.3)
        addChild(personagem);
        
        
        var frames = [SKTexture]();
        for i in 3...23{
            let textName = "mark-sprite-Walk\(i)"
            frames.append(SKTexture(imageNamed: textName))
        }
        let andar = SKAction.animate(with: frames, timePerFrame: 1);
        personagem.run(SKAction.repeatForever(andar));
    }
    
    
    func touchDown(atPoint pos : CGPoint) {
        let mover = SKAction.move(to: pos, duration: 1.0);
        personagem.run(mover);
    }
    
    func touchMoved(toPoint pos : CGPoint) {
            }
    
    func touchUp(atPoint pos : CGPoint) {
            }
    
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        
        for t in touches { self.touchDown(atPoint: t.location(in: self)) }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchMoved(toPoint: t.location(in: self)) }
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchUp(atPoint: t.location(in: self)) }
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
        for t in touches { self.touchUp(atPoint: t.location(in: self)) }
    }
    
    var pontos = 0;
    
    override func update(_ currentTime: TimeInterval) {
        drop(shape: circulo, speed: 3);
        drop(shape: caixa, speed: 1);
        drop(shape: noPath, speed: 5);
        
        // Called before each frame is rendered
        if personagem.intersects(circulo){
            pontos += 3
            placar.text = "Placar:\(pontos)"
            returnTop(shape: circulo)
            
        }
        if personagem.intersects(caixa){
            pontos += 1;
            placar.text = "placar:\(pontos)"
            returnTop(shape: caixa)
        }
        if(personagem.intersects(noPath)){
            pontos -= 5;
            placar.text = "placar:\(pontos)"
            returnTop(shape: noPath)
        }
        if caixa.position.y <= -frame.height + caixa.frame.height {
            returnTop(shape: caixa)
        }
        if circulo.position.y <= -frame.height + circulo.frame.height {
            returnTop(shape: circulo)
        }
        if noPath.position.y <= -frame.height + noPath.frame.height {
            returnTop(shape: noPath)
        }
        
    }
    
    func drop(shape: SKShapeNode, speed: CGFloat){
        shape.position.y -= speed;
        if shape.position.y > frame.height {
            returnTop(shape: shape)
        }
    }
    
    func returnTop(shape: SKShapeNode) {
        let w = UInt32(frame.width);
        let pos = arc4random()%w
        let intRand = Int(pos) - Int(frame.width/2)
        shape.position = CGPoint(x: CGFloat(intRand) ,y: (frame.height * 0.5))
    }
}
