//
//  GameScene.swift
//  Aula3
//
//  Created by DEVELOPER on 05/06/17.
//  Copyright © 2017 lucas. All rights reserved.
//

import SpriteKit
import GameplayKit

class GameScene: SKScene, SKPhysicsContactDelegate {
    
    let 🌶 = SKLabelNode(fontNamed: "Apple Color Emoji")
    let 🔥 = SKLabelNode(fontNamed: "Apple Color Emoji")
    let 💩 = SKLabelNode(fontNamed: "Apple Color Emoji")
    let 🍎 = SKShapeNode(circleOfRadius: 10);
    let category🌶: UInt32 = 0x1 << 0
    let category🔥: UInt32 = 0x1 << 1
    
    
    override func didMove(to view: SKView) {
        let edge = SKShapeNode()
        let edgePath = CGPath(rect: self.frame, transform: nil)
        edge.path = edgePath
        edge.strokeColor = #colorLiteral(red: 0.9607843161, green: 0.7058823705, blue: 0.200000003, alpha: 1)
        edge.lineWidth = 1
        
        edge.physicsBody = SKPhysicsBody(edgeLoopFrom: self.frame)
        
        self.addChild(edge)
        
        let floor = SKSpriteNode(color: #colorLiteral(red: 0.3098039329, green: 0.2039215714, blue: 0.03921568766, alpha: 1), size: CGSize(width: self.frame.width, height: 35))
        floor.anchorPoint = .zero
        floor.position = .zero
        
        floor.physicsBody = SKPhysicsBody(edgeLoopFrom: floor.frame)
        floor.physicsBody?.isDynamic = false
        floor.physicsBody?.friction = 1
        
        self.addChild(floor)
        
        self.backgroundColor = #colorLiteral(red: 0.9098039269, green: 0.4784313738, blue: 0.6431372762, alpha: 1)
        
        for i in 1...13{
            addChild(createBox(CGPoint(x: 1200, y: 51*i)))
            
        }
        
        self.🌶.fontSize = 48
        self.🌶.text = ""
        self.🌶.position = CGPoint(x: 1200, y: 51 * 13)
        self.🌶.physicsBody = SKPhysicsBody(rectangleOf: self.🌶.frame.size, center: CGPoint(x: 0, y: self.🌶.frame.height * 0.5 * 0.8))
        self.🌶.physicsBody?.isDynamic = true
        self.🌶.physicsBody?.categoryBitMask = category🌶
        self.🌶.physicsBody?.contactTestBitMask = category🔥
        addChild(self.🌶)
        
        self.🔥.fontSize = 48
        self.🔥.text = "🔥"
        self.🔥.position = CGPoint(x: 1100, y: 51)
        self.🔥.physicsBody = SKPhysicsBody(rectangleOf: self.🔥.frame.size, center: CGPoint(x: 0, y: self.🔥.frame.height * 0.5 * 0.8))
        self.🔥.physicsBody?.isDynamic = true
        self.🔥.physicsBody?.contactTestBitMask = category🌶
        self.🔥.physicsBody?.categoryBitMask = category🔥
        
        addChild(self.🔥)

        let sling = SKSpriteNode(color: #colorLiteral(red: 0.1215686277, green: 0.01176470611, blue: 0.4235294163, alpha: 1), size: CGSize(width: 20, height: 150))
        sling.anchorPoint = .zero
        sling.position = CGPoint(x: 140, y: 35)
        addChild(sling)
        
        self.💩.fontSize = 48
        self.💩.text = "💩"
        self.💩.position = CGPoint(x: 150, y: 180)
        self.💩.physicsBody = SKPhysicsBody(rectangleOf: self.💩.frame.size, center: CGPoint(x: 0, y: self.💩.frame.height * 0.5 * 0.8))
        self.💩.physicsBody?.isDynamic = false
        self.💩.physicsBody?.allowsRotation = false
        self.💩.physicsBody?.friction = 1.0
        self.💩.physicsBody?.restitution = 0.9
        addChild(self.💩)
        
        self.🍎.fillColor = #colorLiteral(red: 0.9254902005, green: 0.2352941185, blue: 0.1019607857, alpha: 1)
        
        physicsWorld.contactDelegate = self
        
        if let particle = SKEmitterNode(fileNamed: "🔥.sks"){
            particle.zPosition = 4;
        
            🔥.addChild(particle)
        }
        
    }
    func didBegin(_ contact: SKPhysicsContact) {
        let collision = contact.bodyA.categoryBitMask | contact.bodyB.categoryBitMask
        
        if collision == category🌶 | category🔥 {
            print("Voce ganhou")
            contact.bodyA.node?.removeFromParent()
            contact.bodyB.node?.removeFromParent()
        }
    }
    
    func createBox(_ location: CGPoint) -> SKSpriteNode {
        let box = SKSpriteNode(color: #colorLiteral(red: 0.1764705926, green: 0.01176470611, blue: 0.5607843399, alpha: 1), size: CGSize(width: 50, height: 50))
        box.position = location
        box.physicsBody = SKPhysicsBody(rectangleOf: box.size)
        box.physicsBody?.isDynamic = true
        return box
    }
    

    var beginTouch = CGPoint.zero
    override func touchesBegan(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
            beginTouch = touch.location(in: self)
            🍎.position = beginTouch
            addChild(🍎)
        }
    }
    
    override func touchesMoved(_ touches: Set<UITouch>, with event: UIEvent?) {
        if let touch = touches.first {
            🍎.position = touch.location(in: self)
        }
        
    }
    
    override func touchesEnded(_ touches: Set<UITouch>, with event: UIEvent?) {
       if let touch = touches.first {
            🍎.removeFromParent()
            let endTouch = touch.location(in: self)
            let dX = beginTouch.x - endTouch.x
            let dY = beginTouch.y - endTouch.y
            💩.physicsBody?.isDynamic = true
            💩.physicsBody?.applyForce(CGVector(dx: 50 * dX, dy: 50 * dY))
        }
    }
    
    override func touchesCancelled(_ touches: Set<UITouch>, with event: UIEvent?) {
       
    }
    
    
    override func update(_ currentTime: TimeInterval) {
        // Called before each frame is rendered
    }
}
